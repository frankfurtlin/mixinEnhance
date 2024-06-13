package com.frankfurtlin.mixinenhance.mixin.entity.animal;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 9:21
 */
@Mixin(BeeEntity.class)
public class BeeEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数调整蜜蜂的血量、飞行速度、移动速度、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createBeeAttributes() {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (10.0 * Math.sqrt(index));
        double flyingSpeed = 0.6 * (1 + index / 10.0);
        double speed = 0.3 * (1 + index / 10.0);
        double attack = (int) (2.0 * Math.sqrt(index));
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, health)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, flyingSpeed)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, attack)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
    }
}
