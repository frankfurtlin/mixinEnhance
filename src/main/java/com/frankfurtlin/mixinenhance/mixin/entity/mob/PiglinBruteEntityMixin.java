package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 17:36
 */
@Mixin(PiglinBruteEntity.class)
public abstract class PiglinBruteEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改疣猪兽的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createPiglinBruteAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.MAX_HEALTH, 50.0 * index)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.35F)
            .add(EntityAttributes.ATTACK_DAMAGE, 7.0 * index)
            .add(EntityAttributes.FOLLOW_RANGE, 12.0);
    }
}
