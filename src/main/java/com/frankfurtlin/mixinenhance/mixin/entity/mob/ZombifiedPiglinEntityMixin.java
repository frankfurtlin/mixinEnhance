package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
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
     * @reason 根据难度系数修改僵尸猪灵的血量、移动速度、攻击伤害
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createZombifiedPiglinAttributes() {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return ZombieEntity.createZombieAttributes().add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (20.0 * Math.sqrt(index));
        double speed = 0.23 * (1 + index / 10.0);
        double attack = (int) (5.0 * Math.sqrt(index));
        return ZombieEntity.createZombieAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, health)
            .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, attack);
    }
}
