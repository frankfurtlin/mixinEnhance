package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 17:36
 */
@Mixin(CaveSpiderEntity.class)
public abstract class CaveSpiderEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改洞穴蜘蛛的血量
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createCaveSpiderAttributes() {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return SpiderEntity.createSpiderAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0);

        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (12.0 * Math.sqrt(index));
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, health);
    }
}
