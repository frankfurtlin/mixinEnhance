package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/12 19:13
 */
@Mixin(GhastEntity.class)
public abstract class GhastEntityMixin {
    @Shadow private int fireballStrength;

    // 根据难度系数修改火球爆炸威力
    @Inject(method = "<init>", at = @At("TAIL"))
    private void fireballStrengthFactor(EntityType<? extends GhastEntity> entityType,
                                        World world, CallbackInfo ci){
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return;
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        fireballStrength = (int) Math.sqrt(index);
    }

    // 修改恶魂生成率
    @ModifyConstant(method = "canSpawn", constant = @Constant(intValue = 20))
    private static int spawnRate(int constant){
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return constant;
        }
        return 20 / ModMenuConfig.INSTANCE.entityModuleConfig.hostileMobConfig.ghastSpawnFactor;
    }
}
