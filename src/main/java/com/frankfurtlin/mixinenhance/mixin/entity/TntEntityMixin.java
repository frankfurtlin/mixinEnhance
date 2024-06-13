package com.frankfurtlin.mixinenhance.mixin.entity;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.TntEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Random;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/12 7:37
 */
@Mixin(TntEntity.class)
public abstract class TntEntityMixin{
    // TNT爆炸强度修改
    @ModifyConstant(method = "explode", constant = @Constant(floatValue = 4.0F))
    private float explode(float constant){
        if(!ModMenuConfig.INSTANCE.itemModuleConfig.tntConfig.enableRandomExplode){
            return constant;
        }
        int value = new Random().nextInt(ModMenuConfig.INSTANCE.itemModuleConfig.tntConfig.minExplodeRadius,
            ModMenuConfig.INSTANCE.itemModuleConfig.tntConfig.maxExplodeRadius);
        return (float)value;
    }

}
