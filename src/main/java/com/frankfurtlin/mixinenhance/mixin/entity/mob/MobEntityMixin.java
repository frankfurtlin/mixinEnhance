package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.EquipmentDropChances;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/12 15:32
 */
@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
    @Shadow
    private EquipmentDropChances equipmentDropChances;

    // 修改怪物死亡时工具及盔甲掉落的概率
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        this.equipmentDropChances = new EquipmentDropChances(Util.mapEnum(EquipmentSlot.class,
            slot -> MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.armorAndHandDropChance));
    }

    // 根据难度系数修改怪物死亡时掉落的经验
    @Inject(method = "getExperienceToDrop", at = @At(value = "RETURN"), cancellable = true)
    private void difficultyIndex2XpDrop(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex * cir.getReturnValue());
    }

    // 修改怪物生成时自带盔甲的概率
    @ModifyConstant(method = "initEquipment", constant = @Constant(floatValue = 0.15F))
    private float spawnEquipmentChance(float chance) {
        return MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.spawnEquipmentChance;
    }

    // 修改怪物生成时自带盔甲的等级（皮、金、锁链、铁、钻石、下届合金）
    @Inject(method = "getEquipmentForSlot", at = @At("HEAD"), cancellable = true)
    private static void modifyEquipmentForSlot(EquipmentSlot equipmentSlot, int equipmentLevel, CallbackInfoReturnable<Item> cir) {
        if (MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.enableMobArmorEnhancement) {
            Random random = new Random();
            int level = random.nextInt(6);
            switch (equipmentSlot) {
                case HEAD:
                    if (level == 0) {
                        cir.setReturnValue(Items.LEATHER_HELMET);
                    } else if (level == 1) {
                        cir.setReturnValue(Items.GOLDEN_HELMET);
                    } else if (level == 2) {
                        cir.setReturnValue(Items.CHAINMAIL_HELMET);
                    } else if (level == 3) {
                        cir.setReturnValue(Items.IRON_HELMET);
                    } else if (level == 4) {
                        cir.setReturnValue(Items.DIAMOND_HELMET);
                    } else {
                        cir.setReturnValue(Items.NETHERITE_HELMET);
                    }
                case CHEST:
                    if (level == 0) {
                        cir.setReturnValue(Items.LEATHER_CHESTPLATE);
                    } else if (level == 1) {
                        cir.setReturnValue(Items.GOLDEN_CHESTPLATE);
                    } else if (level == 2) {
                        cir.setReturnValue(Items.CHAINMAIL_CHESTPLATE);
                    } else if (level == 3) {
                        cir.setReturnValue(Items.IRON_CHESTPLATE);
                    } else if (level == 4) {
                        cir.setReturnValue(Items.DIAMOND_CHESTPLATE);
                    } else {
                        cir.setReturnValue(Items.NETHERITE_CHESTPLATE);
                    }
                case LEGS:
                    if (level == 0) {
                        cir.setReturnValue(Items.LEATHER_LEGGINGS);
                    } else if (level == 1) {
                        cir.setReturnValue(Items.GOLDEN_LEGGINGS);
                    } else if (level == 2) {
                        cir.setReturnValue(Items.CHAINMAIL_LEGGINGS);
                    } else if (level == 3) {
                        cir.setReturnValue(Items.IRON_LEGGINGS);
                    } else if (level == 4) {
                        cir.setReturnValue(Items.DIAMOND_LEGGINGS);
                    } else {
                        cir.setReturnValue(Items.NETHERITE_LEGGINGS);
                    }
                case FEET:
                    if (level == 0) {
                        cir.setReturnValue(Items.LEATHER_BOOTS);
                    } else if (level == 1) {
                        cir.setReturnValue(Items.GOLDEN_BOOTS);
                    } else if (level == 2) {
                        cir.setReturnValue(Items.CHAINMAIL_BOOTS);
                    } else if (level == 3) {
                        cir.setReturnValue(Items.IRON_BOOTS);
                    } else if (level == 4) {
                        cir.setReturnValue(Items.DIAMOND_BOOTS);
                    } else {
                        cir.setReturnValue(Items.NETHERITE_BOOTS);
                    }
            }
        }
    }

    // 修改怪物生成时主手工具附魔的概率
    @ModifyConstant(method = "enchantMainHandItem", constant = @Constant(floatValue = 0.25F))
    private float enchantMainHandItemChance(float chance) {
        return MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.enchantmentMainHandChance;
    }

    // 修改怪物生成时盔甲附魔的概率
    @ModifyConstant(method = "enchantEquipment*", constant = @Constant(floatValue = 0.5F))
    private float enchantmentArmorChance(float chance) {
        return MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.enchantmentArmorChance;
    }

    // 根据难度系数修改单个区块怪物的数量
    @ModifyConstant(method = "getLimitPerChunk", constant = @Constant(intValue = 4))
    private int difficultyIndex2LimitPerChunk(int limit) {
        return MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex * limit;
    }
}
