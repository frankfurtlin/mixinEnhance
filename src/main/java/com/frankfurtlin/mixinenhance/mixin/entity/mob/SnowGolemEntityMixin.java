package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 8:26
 */
@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin extends GolemEntity {
    protected SnowGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    // 根据难度系数修改雪傀儡的血量
    @Inject(method = "<init>", at = @At("TAIL"))
    private void customHealth(EntityType<? extends SnowGolemEntity> entityType, World world, CallbackInfo ci) {
        if (!MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return;
        }
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (4.0 * Math.sqrt(index));
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(health);
        this.setHealth((float) health);
    }

    // 根据难度系数修改雪人的雪球伤害
    @ModifyConstant(method = "shootAt", constant = @Constant(floatValue = 1.6f))
    private float shootAt(float original) {
        if(!MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.enableCustomMobLogic){
            return original;
        }
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return (float) (original * Math.sqrt(index));
    }
}
