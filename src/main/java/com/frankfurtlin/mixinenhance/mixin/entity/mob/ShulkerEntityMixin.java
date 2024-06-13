package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 8:17
 */
@Mixin(ShulkerEntity.class)
public abstract class ShulkerEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改潜影壳的血量
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createShulkerAttributes() {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int)(30.0 * Math.sqrt(index));
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, health);
    }
}
