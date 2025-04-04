package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/12 19:13
 */
@Mixin(GhastEntity.class)
public abstract class GhastEntityMixin extends FlyingEntity {
    @Shadow private int fireballStrength;

    protected GhastEntityMixin(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改恶魂的血量
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createGhastAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return MobEntity.createMobAttributes().
            add(EntityAttributes.MAX_HEALTH, 10.0 * index).
            add(EntityAttributes.FOLLOW_RANGE, 100.0);
    }

    // 根据难度系数修改恶魂的火球爆炸威力
    @Inject(method = "<init>", at = @At("TAIL"))
    private void fireballStrengthFactor(EntityType<? extends GhastEntity> entityType,
                                        World world, CallbackInfo ci){
        fireballStrength = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
    }

    // 修改恶魂生成率
    @ModifyConstant(method = "canSpawn", constant = @Constant(intValue = 20))
    private static int spawnRate(int constant){
        return 20 / MixinEnhanceClient.getConfig().entityModuleConfig.hostileMobConfig.ghastSpawnFactor;
    }
}
