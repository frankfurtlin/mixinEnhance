package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 8:26
 */
@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改雪人的血量、移动速度
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createSnowGolemAttributes() {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int)(4.0 * Math.sqrt(index));
        double speed = 0.2 * (1 + index / 10.0);
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, health)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed);
    }

    // 根据难度系数修改雪人的雪球伤害
    @ModifyConstant(method = "shootAt", constant = @Constant(floatValue = 1.6f))
    private float shootAt(float original) {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return original;
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        return (float) (original * Math.sqrt(index));
    }
}
