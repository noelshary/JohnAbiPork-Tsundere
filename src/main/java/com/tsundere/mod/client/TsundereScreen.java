package com.tsundere.mod.client;

import com.tsundere.mod.TsundereMod;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class TsundereScreen extends Screen {
    private final int entityId;

    public TsundereScreen(int entityId) {
        super(Text.literal("Tsundere"));
        this.entityId = entityId;
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 70, y = this.height / 2 - 45;
        addButton(x, y,      "Folge mir",        0);
        addButton(x, y + 25, "Bleib stehen",     1);
        addButton(x, y + 50, "Mit ihr reden",    2);
        addButton(x, y + 75, "Essen schenken",   3);
    }

    private void addButton(int x, int y, String label, int action) {
        addDrawableChild(ButtonWidget.builder(Text.literal(label), b -> {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(entityId);
            buf.writeInt(action);
            ClientPlayNetworking.send(TsundereMod.ACTION, buf);
            if (action == 0 || action == 1) this.close(); // Menü nach Folgen/Stehen schließen
        }).dimensions(x, y, 140, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx);
        ctx.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 65, 0xFFFFFF);
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
