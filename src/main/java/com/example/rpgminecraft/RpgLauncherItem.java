package com.example.rpgminecraft;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RpgLauncherItem extends Item {
    private final RpgRocketEntity.RocketMode rocketMode;

    public RpgLauncherItem(Properties properties, RpgRocketEntity.RocketMode rocketMode) {
        super(properties);
        this.rocketMode = rocketMode;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            RpgRocketEntity rocket = new RpgRocketEntity(level, player, rocketMode);
            rocket.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, rocketMode.velocity, 0.4f);
            level.addFreshEntity(rocket);
        }

        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.FIREWORK_ROCKET_LAUNCH,
                SoundSource.PLAYERS,
                0.8f,
                0.8f + level.random.nextFloat() * 0.4f
        );

        player.awardStat(Stats.ITEM_USED.get(this));
        EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
        itemStack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(slot));

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
