package com.tsundere.mod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TsundereMod implements ModInitializer {
    public static final String MOD_ID = "tsundere";
    public static final Identifier OPEN_MENU = new Identifier(MOD_ID, "open_menu");
    public static final Identifier ACTION = new Identifier(MOD_ID, "action");

    public static final EntityType<TsundereEntity> TSUNDERE = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(MOD_ID, "tsundere"),
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(TsundereEntity::new)
                    .spawnGroup(SpawnGroup.CREATURE)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.8f))
                    .build());

    public static final Item SPAWN_EGG = Registry.register(
            Registries.ITEM, new Identifier(MOD_ID, "tsundere_spawn_egg"),
            new SpawnEggItem(TSUNDERE, 0x9FD8FF, 0xFFFFFF, new FabricItemSettings()));

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(TSUNDERE, TsundereEntity.createAttributes());
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(e -> e.add(SPAWN_EGG));

        // Menü-Aktion vom Client -> Server
        ServerPlayNetworking.registerGlobalReceiver(ACTION, (server, player, handler, buf, sender) -> {
            int id = buf.readInt();
            int action = buf.readInt();
            server.execute(() -> {
                Entity e = player.getServerWorld().getEntityById(id);
                if (e instanceof TsundereEntity t && t.squaredDistanceTo(player) < 100) {
                    t.handleAction(player, action);
                }
            });
        });
    }
}
