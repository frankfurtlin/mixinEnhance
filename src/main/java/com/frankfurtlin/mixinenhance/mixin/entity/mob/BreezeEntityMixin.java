package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.BreezeEntity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/21 17:44
 */
@Mixin(BreezeEntity.class)
public abstract class BreezeEntityMixin{
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改旋风人的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createBreezeAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.MOVEMENT_SPEED, 0.63F)
            .add(EntityAttributes.MAX_HEALTH, 30.0 * index)
            .add(EntityAttributes.FOLLOW_RANGE, 24.0)
            .add(EntityAttributes.ATTACK_DAMAGE, 3.0 * index);
    }
}
