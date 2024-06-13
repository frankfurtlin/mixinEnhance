package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/12 15:46
 */
@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity {
    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    // 修改怪物拾取物品的概率
    @ModifyConstant(method = "initialize", constant = @Constant(floatValue = 0.55F))
    private float pickupLootChance(float chance) {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return chance;
        }
        return (float)ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.pickupLootChance;
    }

    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改僵尸的血量、移动速度、攻击伤害、盔甲
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createZombieAttributes() {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0)
                .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
        }

        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (20.0 * Math.sqrt(index));
        double speed = 0.23 * (1 + index / 10.0);
        double attack = (int) (3.0 * Math.sqrt(index));
        double armor = (int) (2.0 * Math.sqrt(index));
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, health)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, speed)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, attack)
            .add(EntityAttributes.GENERIC_ARMOR, armor)
            .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }

    /**
     * @author frankfurtlin
     * @reason 修改僵尸生成时自带的武器概率，同时修改僵尸武器（铁锹、铁剑）->
     *                                 （铁锹、铁镐、铁剑、钻石锹、钻石镐、钻石剑、下届合金锹、下届合金镐、下届合金剑）
     */
    @Overwrite
    public void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        float f = random.nextFloat();
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            float f2 = this.getWorld().getDifficulty() == Difficulty.HARD ? 0.05f : 0.01f;
            if (f < f2) {
                int i = random.nextInt(3);
                if (i == 0) {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
                } else {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
                }
            }
        }else{
            float rate = (float)ModMenuConfig.INSTANCE.entityModuleConfig.hostileMobConfig.zombieSpawnWithTool;
            float f2 = this.getWorld().getDifficulty() == Difficulty.HARD ? rate : rate / 5;
            if (f < f2) {
                int i = random.nextInt(9);
                if (i == 0) {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
                } else if(i == 1) {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
                }else if(i == 2) {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
                }else if(i == 3) {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
                }else if(i == 4) {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SHOVEL));
                }else if(i == 5) {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_PICKAXE));
                }else if(i == 6) {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_SWORD));
                }else if(i == 7) {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_SHOVEL));
                }else if(i == 8) {
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_PICKAXE));
                }
            }
        }

    }
}
