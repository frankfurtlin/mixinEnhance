package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WitchEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 17:36
 */
@Mixin(WitchEntity.class)
public abstract class WitchEntityMixin {

    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改女巫的血量
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createWitchAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes().
            add(EntityAttributes.MAX_HEALTH, 26.0 * index).
            add(EntityAttributes.MOVEMENT_SPEED, 0.25);
    }
}
