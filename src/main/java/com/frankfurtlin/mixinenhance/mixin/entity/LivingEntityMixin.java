package com.frankfurtlin.mixinenhance.mixin.entity;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/12 10:54
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    // 不死图腾在背包中生效
    @Redirect(method = "tryUseTotem", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/entity/LivingEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack inventoryTotemEnabled(LivingEntity livingEntity, Hand hand) {
        if (MixinEnhanceClient.getConfig().itemModuleConfig.inventoryTotemEnabled && livingEntity instanceof PlayerEntity) {
            for (int i = 0; i < ((PlayerEntity) livingEntity).getInventory().size(); i++) {
                ItemStack itemStack = ((PlayerEntity) livingEntity).getInventory().getStack(i);
                if (itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
                    return itemStack;
                }
            }
        }
        return livingEntity.getStackInHand(hand);
    }
}


