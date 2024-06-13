package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 10:30
 */
@Mixin(SilverfishEntity.class)
public abstract class SilverfishEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改蠹虫的血量、移动速度、攻击伤害
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createSilverfishAttributes() {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (8.0 * Math.sqrt(index));
        double speed = 0.25 * (1 + index / 10.0);
        double attack = (int) (Math.sqrt(index));
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, health)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, attack);
    }
}
