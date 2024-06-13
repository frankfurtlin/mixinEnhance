package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.BoggedEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 14:22
 */
@Mixin(BoggedEntity.class)
public abstract class BoggedEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改沼骸的血量
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createBoggedAttributes() {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return AbstractSkeletonEntity.createAbstractSkeletonAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int)(16.0 * Math.sqrt(index));
        return AbstractSkeletonEntity.createAbstractSkeletonAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, health);
    }
}
