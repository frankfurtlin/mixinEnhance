package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 15:14
 */
@Mixin(VindicatorEntity.class)
public abstract class VindicatorEntityMixin extends RaiderEntity {
    protected VindicatorEntityMixin(EntityType<? extends RaiderEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改卫道士的血量、移动速度
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createVindicatorAttributes() {
        if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0);
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (24.0 * Math.sqrt(index));
        double speed = 0.35 * (1 + index / 10.0);
        double attack = (int) (5.0 * Math.sqrt(index));
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, health)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, attack);
    }

    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改卫道士的装备（铁斧->钻石斧->下届合金斧）
     */
    @Overwrite
    public void initEquipment(Random random, LocalDifficulty localDifficulty) {
        if (this.getRaid() == null) {
            if (!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
            } else {
                int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
                if (index < 3) {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
                } else if (index < 8) {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_AXE));
                } else {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_AXE));
                }
            }
        }
    }
}
