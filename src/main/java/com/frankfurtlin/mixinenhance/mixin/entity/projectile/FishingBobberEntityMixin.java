package com.frankfurtlin.mixinenhance.mixin.entity.projectile;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/14 12:03
 */
@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends ProjectileEntity {
    @Shadow
    public abstract @Nullable PlayerEntity getPlayerOwner();

    @Shadow public abstract int use(ItemStack usedItem);

    public FishingBobberEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    // 自动钓鱼
    @Inject(method = "tick", at = @At(value = "INVOKE", ordinal = 2, shift = At.Shift.AFTER,
        target = "Lnet/minecraft/entity/projectile/FishingBobberEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    private void autoFishing(CallbackInfo ci) {
        if(!ModMenuConfig.INSTANCE.defaultModuleConfig.enableAutoFishing){
            return;
        }
        PlayerEntity user = this.getPlayerOwner();
        if (!this.getWorld().isClient && user != null && user.fishHook != null) {
            ItemStack itemStack = user.getStackInHand(Hand.MAIN_HAND);
            int i = this.use(itemStack);
            itemStack.damage(i, user, LivingEntity.getSlotForHand(Hand.MAIN_HAND));

            this.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL,
                1.0f, 0.4f / (this.getWorld().getRandom().nextFloat() * 0.4f + 0.8f));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
            itemStack.use(this.getWorld(), user, Hand.MAIN_HAND);
        }
    }
}
