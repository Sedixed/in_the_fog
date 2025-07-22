package net.sedixed.in_the_fog.entity.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sedixed.in_the_fog.entity.custom.LostEntity;
import net.sedixed.in_the_fog.entity.variant.LostVariant;

public class LostRenderer extends HumanoidMobRenderer<LostEntity, LostModel<LostEntity>> {
    public LostRenderer(EntityRendererProvider.Context context) {
        super(context, new LostModel<>(context.bakeLayer(ModModelLayers.LOST_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(LostEntity entity) {
        return LostVariant.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
