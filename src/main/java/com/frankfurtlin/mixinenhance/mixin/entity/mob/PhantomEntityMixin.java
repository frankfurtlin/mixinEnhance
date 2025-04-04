package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.mob.PhantomEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/21 15:27
 */
@Mixin(PhantomEntity.class)
public abstract class PhantomEntityMixin {
    /**
     * @author frankslin
     * @reason 根据难度系数修改幻翼的攻击力
     */
    @ModifyArgs(method = "onSizeChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeInstance;setBaseValue(D)V"))
    private void injected(Args args) {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        double a0 = args.get(0);
        args.set(0, a0 * index);
    }
}
