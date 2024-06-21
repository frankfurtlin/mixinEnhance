package com.frankfurtlin.mixinenhance.mixin.entity;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/17 10:36
 */
@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;

    @Shadow abstract void checkWaterState();

    @Shadow public abstract World getWorld();

    @Shadow public abstract boolean updateMovementInFluid(TagKey<Fluid> tag, double speed);

    @Shadow public abstract boolean isTouchingWater();

    @Shadow public abstract boolean isSneaking();

    /**
     * @author frankfurtlin
     * @reason 修改玩家在岩浆中的移动速度
     */
    @Overwrite
    public boolean updateWaterState() {
        this.fluidHeight.clear();
        this.checkWaterState();
        double d;
        Entity entity = (Entity)(Object)this;
        if(entity instanceof ServerPlayerEntity && ModMenuConfig.INSTANCE.defaultModuleConfig.enablePlayerNoSlowInLava){
            d = 1.0;
        }
        else{
            d = this.getWorld().getDimension().ultrawarm() ? 0.007 : 0.0023333333333333335;
        }
        boolean bl = this.updateMovementInFluid(FluidTags.LAVA, d);
        return this.isTouchingWater() || bl;
    }

    // 修改玩家在水中的移动速度
    @ModifyConstant(method = "checkWaterState", constant = @Constant(doubleValue = 0.014))
    private double enablePlayerNoSlowInWater(double original){
        Entity entity = (Entity)(Object)this;
        if(entity instanceof ServerPlayerEntity && ModMenuConfig.INSTANCE.defaultModuleConfig.enablePlayerNoSlowInWater){
            return 1.0;
        }
        return original;
    }
}
