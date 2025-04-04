package com.frankfurtlin.mixinenhance.mixin.entity.animal;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 9:21
 */
@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin{
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改蜜蜂的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createBeeAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return AnimalEntity.createAnimalAttributes()
            .add(EntityAttributes.MAX_HEALTH, 10.0 * index)
            .add(EntityAttributes.FLYING_SPEED, 0.6F)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.3F)
            .add(EntityAttributes.ATTACK_DAMAGE, 2.0 * index);
    }
}
