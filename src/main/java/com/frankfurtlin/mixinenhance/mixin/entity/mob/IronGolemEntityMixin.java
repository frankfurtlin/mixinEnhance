package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 8:32
 */
@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改铁傀儡的血量、移动速度、攻击伤害
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createIronGolemAttributes() {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1.0);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (100.0 * Math.sqrt(index));
        double speed = 0.25 * (1 + index / 10.0);
        double attack = (int) (15.0 * Math.sqrt(index));
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, health)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0).
            add(EntityAttributes.GENERIC_ATTACK_DAMAGE, attack)
            .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1.0);
    }
}
