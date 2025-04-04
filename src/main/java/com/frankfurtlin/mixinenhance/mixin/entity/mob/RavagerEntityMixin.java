package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.RavagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 14:43
 */
@Mixin(RavagerEntity.class)
public abstract class RavagerEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改劫掠兽的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createRavagerAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.MAX_HEALTH, 100.0 * index)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.3)
            .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.75)
            .add(EntityAttributes.ATTACK_DAMAGE, 12.0 * index)
            .add(EntityAttributes.ATTACK_KNOCKBACK, 1.5)
            .add(EntityAttributes.FOLLOW_RANGE, 32.0)
            .add(EntityAttributes.STEP_HEIGHT, 1.0);
    }
}
