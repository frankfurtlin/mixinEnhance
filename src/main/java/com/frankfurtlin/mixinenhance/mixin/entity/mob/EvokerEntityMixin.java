package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
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
     * @reason 根据难度系数修改唤魔者的血量
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createEvokerAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.MOVEMENT_SPEED, 0.5)
            .add(EntityAttributes.FOLLOW_RANGE, 12.0)
            .add(EntityAttributes.MAX_HEALTH, 24.0 * index);
    }
}
