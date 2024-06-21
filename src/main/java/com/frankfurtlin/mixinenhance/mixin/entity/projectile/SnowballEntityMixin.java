package com.frankfurtlin.mixinenhance.mixin.entity.projectile;

import com.frankfurtlin.mixinenhance.MixinEnhanceClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * @author Frankfurtlin
 * @version 1.0
 * @date 2024/6/15 20:30
 */
@Mixin(SnowballEntity.class)
public abstract class SnowballEntityMixin extends ThrownItemEntity{
    public SnowballEntityMixin(EntityType<? extends ThrownItemEntity> entityType, double d, double e, double f, World world) {
        super(entityType, d, e, f, world);
    }

    /**
     * @author frankfurtlin
     * @reason 雪球伤害修改
     */
    @Overwrite
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        int damage = MixinEnhanceClient.getConfig().itemModuleConfig.snowballDamage;
        int i = entity instanceof BlazeEntity ? Math.max(3, damage * 3) : damage;
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), i);
    }
}
