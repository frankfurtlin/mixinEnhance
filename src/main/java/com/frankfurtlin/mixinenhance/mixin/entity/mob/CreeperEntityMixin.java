package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 17:36
 */
@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin {
    @Shadow private int explosionRadius;

    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改苦力怕的血量、移动速度、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createCreeperAttributes() {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (20.0 * Math.sqrt(index));
        double speed = 0.25 * (1 + index / 10.0);
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, health)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed);
    }

    // 修改苦力怕爆炸半径
    @Inject(method = "<init>", at = @At("TAIL"))
    private void creeperExplodeRadius(EntityType<? extends CreeperEntity> entityType, World world, CallbackInfo ci) {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return;
        }
        this.explosionRadius = ModMenuConfig.INSTANCE.entityModuleConfig.hostileMobConfig.creeperExplodeRadius;
    }

    // 修改苦力怕爆炸生成的效果云
    @Inject(method = "spawnEffectsCloud",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            shift = At.Shift.BEFORE))
    private void creeperExplodeEffectCloud(CallbackInfo ci, @Local AreaEffectCloudEntity areaEffectCloudEntity) {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return;
        }
        int explodeRadius = ModMenuConfig.INSTANCE.entityModuleConfig.hostileMobConfig.creeperExplodeRadius;
        areaEffectCloudEntity.setRadius(explodeRadius - 0.5f);
        areaEffectCloudEntity.setRadiusOnUse(-explodeRadius / 5.0f);
    }
}
