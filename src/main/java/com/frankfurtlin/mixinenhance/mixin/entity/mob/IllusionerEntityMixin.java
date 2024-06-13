package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllusionerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 14:43
 */
@Mixin(IllusionerEntity.class)
public abstract class IllusionerEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改幻术师的血量、移动速度、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createIllusionerAttributes() {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 18.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (32.0 * Math.sqrt(index));
        double speed = 0.5 * (1 + index / 10.0);
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 18.0)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, health);
    }

    // 根据难度系数修改幻术师的弓箭伤害
    @ModifyConstant(method = "shootAt", constant = @Constant(floatValue = 1.6f))
    private float shootAt(float original) {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return original;
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        return (float) (original * Math.sqrt(index));
    }
}
