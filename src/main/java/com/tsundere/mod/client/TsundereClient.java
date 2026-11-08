package com.tsundere.mod.client;

import com.tsundere.mod.TsundereMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class TsundereClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(TsundereMod.TSUNDERE, TsundereRenderer::new);
        ClientPlayNetworking.registerGlobalReceiver(TsundereMod.OPEN_MENU, (client, handler, buf, sender) -> {
            int id = buf.readInt();
            client.execute(() -> client.setScreen(new TsundereScreen(id)));
        });
    }
}
