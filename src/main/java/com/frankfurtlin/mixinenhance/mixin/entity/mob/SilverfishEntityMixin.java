package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
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
     * @reason 根据难度系数修改蠹虫的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createSilverfishAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.MAX_HEALTH, 8.0 * index)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.25)
            .add(EntityAttributes.ATTACK_DAMAGE, 1.0 * index);
    }
}
