package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 17:36
 */
@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改蜘蛛的血量
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createSpiderAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes().
            add(EntityAttributes.MAX_HEALTH, 16.0 * index).
            add(EntityAttributes.MOVEMENT_SPEED, 0.3F);
    }

    // 修改蜘蛛生成时带有药水效果的概率
    @ModifyConstant(method = "initialize", constant = @Constant(floatValue = 0.1f))
    private float spiderSpawnWithEffect(float original) {
        return MixinEnhanceClient.getConfig().entityModuleConfig.hostileMobConfig.spiderSpawnWithEffect;
    }

}
