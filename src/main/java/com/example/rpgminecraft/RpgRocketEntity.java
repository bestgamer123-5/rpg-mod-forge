package com.example.rpgminecraft;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.HitResult;

public class RpgRocketEntity extends ThrowableItemProjectile {
    private static final String MODE_NBT = "RocketMode";
    private RocketMode rocketMode = RocketMode.STANDARD;

    public enum RocketMode {
        STANDARD(2.5f, 1.7f),
        NUKE(8.0f, 1.2f),
        HUNTER(1.5f, 2.1f);

        public final float explosionPower;
        public final float velocity;

        RocketMode(float explosionPower, float velocity) {
            this.explosionPower = explosionPower;
            this.velocity = velocity;
        }
    }

    public RpgRocketEntity(EntityType<? extends RpgRocketEntity> entityType, Level level) {
        super(entityType, level);
    }

    public RpgRocketEntity(Level level, LivingEntity owner, RocketMode rocketMode) {
        super(RpgMinecraftMod.RPG_ROCKET.get(), owner, level);
        this.rocketMode = rocketMode;
    }

    @Override
    protected Item getDefaultItem() {
        return RpgMinecraftMod.RPG_LAUNCHER.get();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);

        if (!this.level().isClientSide) {
            if (rocketMode == RocketMode.HUNTER) {
                this.level().getEntitiesOfClass(
                        Animal.class,
                        this.getBoundingBox().inflate(6.0),
                        animal -> true
                ).forEach(animal -> animal.hurt(this.damageSources().explosion(this, this.getOwner()), 20.0f));
            }

            this.level().explode(
                    this,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    rocketMode.explosionPower,
                    Explosion.BlockInteraction.KEEP
            );
            this.discard();
        }
    }

    @Override
    protected float getGravity() {
        return 0.0f;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString(MODE_NBT, rocketMode.name());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(MODE_NBT)) {
            this.rocketMode = RocketMode.valueOf(tag.getString(MODE_NBT));
        }
    }
}
