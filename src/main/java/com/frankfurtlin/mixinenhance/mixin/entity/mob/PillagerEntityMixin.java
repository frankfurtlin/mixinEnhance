package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
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
     * @reason 根据难度系数修改掠夺者的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createPillagerAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.MOVEMENT_SPEED, 0.35F)
            .add(EntityAttributes.MAX_HEALTH, 24.0 * index)
            .add(EntityAttributes.ATTACK_DAMAGE, 5.0 * index)
            .add(EntityAttributes.FOLLOW_RANGE, 32.0);
    }
}
