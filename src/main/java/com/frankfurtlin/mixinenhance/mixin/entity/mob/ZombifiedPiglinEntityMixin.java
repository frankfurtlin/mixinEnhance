package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 12:08
 */
@Mixin(ZombifiedPiglinEntity.class)
public abstract class ZombifiedPiglinEntityMixin {

    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改僵尸猪灵的攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createZombifiedPiglinAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return ZombieEntity.createZombieAttributes()
            .add(EntityAttributes.SPAWN_REINFORCEMENTS, 0.0)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.23F)
            .add(EntityAttributes.ATTACK_DAMAGE, 5.0 * index);
    }
}
