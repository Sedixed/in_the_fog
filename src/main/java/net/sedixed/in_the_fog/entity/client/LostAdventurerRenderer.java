package net.sedixed.in_the_fog.entity.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sedixed.in_the_fog.entity.custom.LostAdventurerEntity;
import net.sedixed.in_the_fog.entity.variant.LostVariant;

public class LostAdventurerRenderer extends HumanoidMobRenderer<LostAdventurerEntity, LostModel<LostAdventurerEntity>> {
    public LostAdventurerRenderer(EntityRendererProvider.Context context) {
        super(context, new LostModel<>(context.bakeLayer(ModModelLayers.LOST_ADVENTURER_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(LostAdventurerEntity entity) {
        return LostVariant.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
