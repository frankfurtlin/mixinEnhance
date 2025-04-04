package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 8:32
 */
@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin{
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改铁傀儡的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createIronGolemAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return MobEntity.createMobAttributes()
            .add(EntityAttributes.MAX_HEALTH, 100.0 * index)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.25)
            .add(EntityAttributes.KNOCKBACK_RESISTANCE, 1.0)
            .add(EntityAttributes.ATTACK_DAMAGE, 15.0 * index)
            .add(EntityAttributes.STEP_HEIGHT, 1.0);
    }
}
