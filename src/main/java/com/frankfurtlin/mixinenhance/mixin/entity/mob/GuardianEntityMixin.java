package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 14:43
 */
@Mixin(GuardianEntity.class)
public abstract class GuardianEntityMixin{
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改守卫者的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createGuardianAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.ATTACK_DAMAGE, 6.0 * index)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.5)
            .add(EntityAttributes.MAX_HEALTH, 30.0 * index);
    }
}
