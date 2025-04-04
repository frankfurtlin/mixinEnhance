package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/21 17:42
 */
@Mixin(ZoglinEntity.class)
public abstract class ZoglinEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改僵尸疣猪兽的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createZoglinAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.MAX_HEALTH, 40.0 * index)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.3F)
            .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.6F)
            .add(EntityAttributes.ATTACK_KNOCKBACK, 1.0)
            .add(EntityAttributes.ATTACK_DAMAGE, 6.0 * index);
    }
}
