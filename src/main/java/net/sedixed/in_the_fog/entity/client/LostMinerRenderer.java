package net.sedixed.in_the_fog.entity.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.sedixed.in_the_fog.entity.custom.LostMinerEntity;
import net.sedixed.in_the_fog.entity.variant.LostVariant;

public class LostMinerRenderer extends HumanoidMobRenderer<LostMinerEntity, LostModel<LostMinerEntity>> {
    public LostMinerRenderer(EntityRendererProvider.Context context) {
        super(context, new LostModel<>(context.bakeLayer(ModModelLayers.LOST_MINER_LAYER)), 0.5f);

        this.addLayer(new HumanoidArmorLayer<>(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(LostMinerEntity entity) {
        return LostVariant.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
