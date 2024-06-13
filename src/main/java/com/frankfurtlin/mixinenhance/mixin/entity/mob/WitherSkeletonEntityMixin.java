package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.config.ModMenuConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 14:12
 */
@Mixin(WitherSkeletonEntity.class)
public abstract class WitherSkeletonEntityMixin extends AbstractSkeletonEntity {
    protected WitherSkeletonEntityMixin(EntityType<? extends AbstractSkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改凋零骷髅的工具剑（石剑->铁剑->钻石剑->下届合金剑）
     */
    @Overwrite
    public void initEquipment(Random random, LocalDifficulty localDifficulty) {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        if(index == 1){
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
        }else if(index < 4){
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        }else if(index < 8){
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
        }else{
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_SWORD));
        }
    }

    // 根据难度系数修改凋零骷髅的攻击力
    @ModifyConstant(method = "initialize", constant = @Constant(doubleValue = 4.0f))
    private double shootAt(double original) {
        if(!ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.enableCustomMobLogic){
            return original;
        }
        int index = ModMenuConfig.INSTANCE.entityModuleConfig.mobConfig.difficultyIndex;
        return (float) (original * Math.sqrt(index));
    }
}
