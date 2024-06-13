package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.GuardianEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 14:43
 */
@Mixin(ElderGuardianEntity.class)
public abstract class ElderGuardianEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改远古守卫者的血量、移动速度、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createElderGuardianAttributes() {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return GuardianEntity.createGuardianAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 80.0);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (80.0 * Math.sqrt(index));
        double speed = 0.3 * (1 + index / 10.0);
        double attack = (int) (8.0 * Math.sqrt(index));
        return GuardianEntity.createGuardianAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, attack)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, health);
    }
}
