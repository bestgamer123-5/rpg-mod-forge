package com.example.rpgminecraft;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(RpgMinecraftMod.MOD_ID)
public class RpgMinecraftMod {
    public static final String MOD_ID = "forge";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<EntityType<RpgRocketEntity>> RPG_ROCKET = ENTITY_TYPES.register(
            "rpg_rocket",
            () -> EntityType.Builder.<RpgRocketEntity>of(RpgRocketEntity::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(ResourceLocation.fromNamespaceAndPath(MOD_ID, "rpg_rocket").toString())
    );

    public static final RegistryObject<Item> RPG_LAUNCHER = ITEMS.register(
            "rpg_launcher", () -> new RpgLauncherItem(new Item.Properties().stacksTo(1), RpgRocketEntity.RocketMode.STANDARD)
    );
    public static final RegistryObject<Item> NUKE_LAUNCHER = ITEMS.register(
            "nuke_launcher", () -> new RpgLauncherItem(new Item.Properties().stacksTo(1), RpgRocketEntity.RocketMode.NUKE)
    );
    public static final RegistryObject<Item> HUNTER_LAUNCHER = ITEMS.register(
            "hunter_launcher", () -> new RpgLauncherItem(new Item.Properties().stacksTo(1), RpgRocketEntity.RocketMode.HUNTER)
    );

    public RpgMinecraftMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);
    }
}
