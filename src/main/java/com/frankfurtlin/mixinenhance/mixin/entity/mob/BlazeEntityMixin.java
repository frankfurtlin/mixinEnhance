package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 13:29
 */
@Mixin(BlazeEntity.class)
public abstract class BlazeEntityMixin{
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改烈焰人的攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createBlazeAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.ATTACK_DAMAGE, 6.0 * index)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.23F)
            .add(EntityAttributes.FOLLOW_RANGE, 48.0);
    }
}
