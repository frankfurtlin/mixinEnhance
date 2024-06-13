package com.frankfurtlin.mixinenhance.mixin.entity;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.decoration.EndCrystalEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Random;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/12 7:37
 */
@Mixin(EndCrystalEntity.class)
public abstract class EndCrystalEntityMixin {
    // 末地水晶爆炸强度修改
    @ModifyConstant(method = "damage", constant = @Constant(floatValue = 6.0F))
    private float damage(float constant){
        if(!ModMenuConfig.INSTANCE.itemModuleConfig.endCrystalConfig.enableRandomExplode){
            return constant;
        }
        int value = new Random().nextInt(ModMenuConfig.INSTANCE.itemModuleConfig.endCrystalConfig.minExplodeRadius,
            ModMenuConfig.INSTANCE.itemModuleConfig.endCrystalConfig.maxExplodeRadius);
        return (float)value;
    }

}
