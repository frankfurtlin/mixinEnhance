package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 14:43
 */
@Mixin(EvokerEntity.class)
public abstract class EvokerEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改唤魔者的血量、移动速度
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createEvokerAttributes() {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (24.0 * Math.sqrt(index));
        double speed = 0.5 * (1 + index / 10.0);
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, health);
    }
}
