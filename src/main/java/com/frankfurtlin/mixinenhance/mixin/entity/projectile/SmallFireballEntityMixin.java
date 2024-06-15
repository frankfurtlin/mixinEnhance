package com.frankfurtlin.mixinenhance.mixin.entity.projectile;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.projectile.SmallFireballEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/15 12:16
 */
@Mixin(SmallFireballEntity.class)
public abstract class SmallFireballEntityMixin {
    // 修改小火球的伤害（火焰弹、烈焰人）
    @ModifyConstant(method = "onEntityHit", constant = @Constant(floatValue = 5.0f))
    private float smallFireballDamage(float original) {
        return ModMenuConfig.INSTANCE.itemModuleConfig.smallFireballDamage;
    }
}
