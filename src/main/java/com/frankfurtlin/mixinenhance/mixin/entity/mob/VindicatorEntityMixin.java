package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 15:14
 */
@Mixin(VindicatorEntity.class)
public abstract class VindicatorEntityMixin extends RaiderEntity{
    protected VindicatorEntityMixin(EntityType<? extends RaiderEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改卫道士的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createVindicatorAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.MOVEMENT_SPEED, 0.35F)
            .add(EntityAttributes.FOLLOW_RANGE, 12.0)
            .add(EntityAttributes.MAX_HEALTH, 24.0 * index)
            .add(EntityAttributes.ATTACK_DAMAGE, 5.0 * index);
    }

    // 卫道士武器升级（铁、钻石、下届合金剑）
    @Inject(method = "initEquipment", at = @At("TAIL"))
    public void initEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        if (this.getRaid() == null &&
            MixinEnhanceClient.getConfig().entityModuleConfig.hostileMobConfig.enableVindicatorWeaponEnhancement) {
            int level = random.nextInt(3);
            if (level == 0) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
            } else if (level == 1) {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_AXE));
            } else {
                this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_AXE));
            }
        }
    }
}
