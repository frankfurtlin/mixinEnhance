package com.frankfurtlin.mixinenhance.mixin.entity.mob;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PiglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/13 17:36
 */
@Mixin(PiglinEntity.class)
public abstract class PiglinEntityMixin {
    /**
     * @author frankfurtlin
     * @reason 根据难度系数修改猪灵的血量、攻击力
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createPiglinAttributes() {
        int index = MixinEnhanceClient.getConfig().entityModuleConfig.mobConfig.difficultyIndex;
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.MAX_HEALTH, 16.0 * index)
            .add(EntityAttributes.MOVEMENT_SPEED, 0.35F)
            .add(EntityAttributes.ATTACK_DAMAGE, 5.0 * index);
    }

    // 猪灵在生成时带有金质盔甲的概率
    @ModifyConstant(method = "equipAtChance", constant = @Constant(floatValue = 0.1f))
    private float piglinSpawnWithArmor(float constant){
        return MixinEnhanceClient.getConfig().entityModuleConfig.hostileMobConfig.piglinSpawnWithArmor;
    }
}
