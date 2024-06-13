package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/12 15:32
 */
@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
    // 修改怪物生成时自带盔甲的概率
    @ModifyConstant(method = "initEquipment", constant = @Constant(floatValue = 0.15F))
    private float spawnEquipmentChance(float chance) {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return chance;
        }
        return (float) ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.spawnEquipmentChance;
    }

    // 修改怪物生成时盔甲的强度（皮、金、锁链、铁、钻石）
    @ModifyConstant(method = "initEquipment", constant = @Constant(floatValue = 0.095F))
    private float enchantmentArmorStrength(float chance) {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return chance;
        }
        return (float) ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enchantmentArmorStrength;
    }

    // 修改怪物生成时盔甲附魔的概率
    @ModifyConstant(method = "enchantEquipment", constant = @Constant(floatValue = 0.5F))
    private float enchantmentArmorChance(float chance) {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return chance;
        }
        return (float) ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enchantmentArmorChance;
    }

    // 修改怪物生成时盔甲附魔的最高等级
    @ModifyConstant(method = "enchantEquipment", constant = @Constant(intValue = 18))
    private int enchantmentArmorLevel(int level) {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return level;
        }
        return ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enchantmentArmorLevel - 5;
    }

    // 修改怪物生成时主手工具附魔的概率
    @ModifyConstant(method = "enchantMainHandItem", constant = @Constant(floatValue = 0.25F))
    private float enchantMainHandItemChance(float chance) {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return chance;
        }
        return (float) ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enchantmentMainHandChance;
    }

    // 修改怪物生成时主手工具附魔的最高等级
    @ModifyConstant(method = "enchantMainHandItem", constant = @Constant(intValue = 18))
    private int enchantmentMainHandLevel(int level) {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return level;
        }
        return ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enchantmentMainHandLevel - 5;
    }

    // 修改怪物死亡时工具及盔甲掉落的概率
    @ModifyConstant(method = "<init>", constant = @Constant(floatValue = 0.085F))
    private float armorAndHandDropChance(float chance) {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return chance;
        }
        return (float) ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.armorAndHandDropChance;
    }

    // 根据难度系数修改怪物死亡时掉落的经验
    @Inject(method = "getXpToDrop", at = @At(value = "RETURN"), cancellable = true)
    private void difficultyIndex2XpDrop(CallbackInfoReturnable<Integer> cir) {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return;
        }
        cir.setReturnValue(ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex * cir.getReturnValue());
    }

    // 根据难度系数修改单个区块怪物的数量
    @ModifyConstant(method = "getLimitPerChunk", constant = @Constant(intValue = 4))
    private int difficultyIndex2LimitPerChunk(int limit) {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return limit;
        }
        return ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex * limit;
    }

    /**
     * @author frankfurtlin
     * @reason 新增怪物盔甲的下届合金版本
     */
    @Overwrite
    public static @Nullable Item getEquipmentForSlot(EquipmentSlot equipmentSlot, int equipmentLevel) {
        equipmentLevel += new Random().nextInt(2);
        switch (equipmentSlot) {
            case HEAD: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_HELMET;
                }
                if (equipmentLevel == 1) {
                    return Items.GOLDEN_HELMET;
                }
                if (equipmentLevel == 2) {
                    return Items.CHAINMAIL_HELMET;
                }
                if (equipmentLevel == 3) {
                    return Items.IRON_HELMET;
                }
                if (equipmentLevel == 4) {
                    return Items.DIAMOND_HELMET;
                }
                if (equipmentLevel == 5) {
                    return Items.NETHERITE_HELMET;
                }
            }
            case CHEST: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_CHESTPLATE;
                }
                if (equipmentLevel == 1) {
                    return Items.GOLDEN_CHESTPLATE;
                }
                if (equipmentLevel == 2) {
                    return Items.CHAINMAIL_CHESTPLATE;
                }
                if (equipmentLevel == 3) {
                    return Items.IRON_CHESTPLATE;
                }
                if (equipmentLevel == 4) {
                    return Items.DIAMOND_CHESTPLATE;
                }
                if (equipmentLevel == 5) {
                    return Items.NETHERITE_CHESTPLATE;
                }
            }
            case LEGS: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_LEGGINGS;
                }
                if (equipmentLevel == 1) {
                    return Items.GOLDEN_LEGGINGS;
                }
                if (equipmentLevel == 2) {
                    return Items.CHAINMAIL_LEGGINGS;
                }
                if (equipmentLevel == 3) {
                    return Items.IRON_LEGGINGS;
                }
                if (equipmentLevel == 4) {
                    return Items.DIAMOND_LEGGINGS;
                }
                if (equipmentLevel == 5) {
                    return Items.NETHERITE_LEGGINGS;
                }
            }
            case FEET: {
                if (equipmentLevel == 0) {
                    return Items.LEATHER_BOOTS;
                }
                if (equipmentLevel == 1) {
                    return Items.GOLDEN_BOOTS;
                }
                if (equipmentLevel == 2) {
                    return Items.CHAINMAIL_BOOTS;
                }
                if (equipmentLevel == 3) {
                    return Items.IRON_BOOTS;
                }
                if (equipmentLevel == 4) {
                    return Items.DIAMOND_BOOTS;
                }
                if (equipmentLevel == 5) {
                    return Items.NETHERITE_BOOTS;
                }
            }
        }
        return null;
    }
}
