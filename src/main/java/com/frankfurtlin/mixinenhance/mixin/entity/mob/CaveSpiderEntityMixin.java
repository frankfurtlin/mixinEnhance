package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.CaveSpiderEntity;
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
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return SpiderEntity.createSpiderAttributes().add(EntityAttributes.MAX_HEALTH, 12.0 * index);
    }
}
