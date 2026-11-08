package com.tsundere.mod.client;

import com.tsundere.mod.TsundereEntity;
import com.tsundere.mod.TsundereMod;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;

/** Rendert die Tsundere mit dem Spieler-Modell + deinem Skin (1:1). */
public class TsundereRenderer extends MobEntityRenderer<TsundereEntity, PlayerEntityModel<TsundereEntity>> {
    // false = Steve-Arme (4px breit), true = Alex-Arme (3px, "slim")
    private static final boolean SLIM_ARMS = false;
    private static final Identifier TEXTURE = new Identifier(TsundereMod.MOD_ID, "textures/entity/tsundere.png");

    public TsundereRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlayerEntityModel<>(
                ctx.getPart(SLIM_ARMS ? EntityModelLayers.PLAYER_SLIM : EntityModelLayers.PLAYER), SLIM_ARMS),
                0.5f);
    }

    @Override
    public Identifier getTexture(TsundereEntity entity) { return TEXTURE; }
}
