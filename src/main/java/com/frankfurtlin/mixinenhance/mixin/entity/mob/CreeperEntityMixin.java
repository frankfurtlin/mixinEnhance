package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 17:36
 */
@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {
    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    private int explosionRadius;

    // 根据难度系数修改苦力怕的血量
    @Inject(method = "<init>", at = @At("TAIL"))
    private void customHealth(EntityType<? extends CreeperEntity> entityType, World world, CallbackInfo ci) {
        if (!MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return;
        }
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (20.0 * Math.sqrt(index));
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.MAX_HEALTH)).setBaseValue(health);
        this.setHealth((float) health);
    }

    // 修改苦力怕爆炸半径
    @Inject(method = "<init>", at = @At("TAIL"))
    private void creeperExplodeRadius(EntityType<? extends CreeperEntity> entityType, World world, CallbackInfo ci) {
        if (!MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return;
        }
        this.explosionRadius = MixinEnhanceClient.getConfig().entityModuleConfig.hostileMobConfig.creeperExplodeRadius;
    }

    // 修改苦力怕爆炸生成的效果云
    @Inject(method = "spawnEffectsCloud",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            shift = At.Shift.BEFORE))
    private void creeperExplodeEffectCloud(CallbackInfo ci, @Local AreaEffectCloudEntity areaEffectCloudEntity) {
        if (!MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return;
        }
        int explodeRadius = MixinEnhanceClient.getConfig().entityModuleConfig.hostileMobConfig.creeperExplodeRadius;
        areaEffectCloudEntity.setRadius(explodeRadius - 0.5f);
        areaEffectCloudEntity.setRadiusOnUse(-explodeRadius / 5.0f);
    }
}
