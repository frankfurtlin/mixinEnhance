package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 14:43
 */
@Mixin(PillagerEntity.class)
public abstract class PillagerEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改掠夺者的血量、移动速度、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createPillagerAttributes() {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (24.0 * Math.sqrt(index));
        double speed = 0.35 * (1 + index / 10.0);
        double attack = (int) (5.0 * Math.sqrt(index));
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, health)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, attack)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0);
    }
}
