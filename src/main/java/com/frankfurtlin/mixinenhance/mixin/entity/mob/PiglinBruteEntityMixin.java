package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 17:36
 */
@Mixin(PiglinBruteEntity.class)
public abstract class PiglinBruteEntityMixin extends AbstractPiglinEntity {
    public PiglinBruteEntityMixin(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    // 根据难度系数修改疣猪兽的血量、攻击力
    @Inject(method = "<init>", at = @At("TAIL"))
    private void customHealthAndAttackDamage(EntityType<? extends PiglinBruteEntity> entityType, World world, CallbackInfo ci){
        if (!MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.enableCustomMobLogic) {
            return;
        }
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        double health = (int) (50.0 * Math.sqrt(index));
        double attack = (int) (7.0 * Math.sqrt(index));
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(health);
        this.setHealth((float) health);
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE)).setBaseValue(attack);
    }
}
