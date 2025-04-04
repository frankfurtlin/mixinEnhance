package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.VexEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 13:54
 */
@Mixin(VexEntity.class)
public abstract class VexEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改恼鬼的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createVexAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes().
            add(EntityAttributes.MAX_HEALTH, 14.0 * index).
            add(EntityAttributes.ATTACK_DAMAGE, 4.0 * index);
    }
}
