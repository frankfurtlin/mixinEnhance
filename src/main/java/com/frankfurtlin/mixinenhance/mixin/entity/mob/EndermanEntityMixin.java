package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 17:36
 */
@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改末影人的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createEndermanAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.MAX_HEALTH, 40.0 * index)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.3F)
            .add(EntityAttributes.ATTACK_DAMAGE, 7.0 * index)
            .add(EntityAttributes.FOLLOW_RANGE, 64.0)
            .add(EntityAttributes.STEP_HEIGHT, 1.0);
    }
}
