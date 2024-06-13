package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
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
     * @reason 根据难度系数修改蜘蛛的血量、移动速度
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createSpiderAttributes() {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (16.0 * Math.sqrt(index));
        double speed = 0.3 * (1 + index / 10.0);
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, health)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed);
    }

    // 修改蜘蛛生成时带有药水效果的概率
    @ModifyConstant(method = "initialize", constant = @Constant(floatValue = 0.1f))
    private float spiderSpawnWithEffect(float original) {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return original;
        }
        return (float) ModMenuConfig.INSTANCE.entityModuleConfig.hostileMobConfig.spiderSpawnWithEffect;
    }

}
