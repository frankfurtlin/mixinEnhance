package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.GuardianEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 14:43
 */
@Mixin(ElderGuardianEntity.class)
public abstract class ElderGuardianEntityMixin{
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改远古守卫者的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createElderGuardianAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return GuardianEntity.createGuardianAttributes()
            .add(EntityAttributes.MOVEMENT_SPEED, 0.3F)
            .add(EntityAttributes.ATTACK_DAMAGE, 8.0 * index)
            .add(EntityAttributes.MAX_HEALTH, 80.0 * index);
    }
}
