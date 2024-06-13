package com.frankfurtlin.mixinenhance.mixin.entity.animal;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 9:44
 */
@Mixin(HoglinEntity.class)
public class HoglinMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改疣猪兽的血量、移动速度、攻击伤害
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createHoglinAttributes() {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (40.0 * Math.sqrt(index));
        double speed = 0.3 * (1 + index / 10.0);
        double attack = (int) (6.0 * Math.sqrt(index));
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, health)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6f)
            .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, attack);
    }
}
