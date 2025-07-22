package net.sedixed.in_the_fog.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sedixed.in_the_fog.InTheFogMod;
import net.sedixed.in_the_fog.entity.ModEntities;
import net.sedixed.in_the_fog.entity.client.*;

@Mod.EventBusSubscriber(modid = InTheFogMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.LOST_MINER_LAYER, LostModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.LOST_ADVENTURER_LAYER, LostModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.LOST_LAYER, LostModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.LOST_FISHERMAN_LAYER, LostModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.LOST_FIGHTER_LAYER, LostModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.LOST_BUILDER_LAYER, LostModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.LOST_FARMER_LAYER, LostModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.LOST_MINER.get(), LostMinerRenderer::new);
        event.registerEntityRenderer(ModEntities.LOST_ADVENTURER.get(), LostAdventurerRenderer::new);
        event.registerEntityRenderer(ModEntities.LOST_FARMER.get(), LostFarmerRenderer::new);
        event.registerEntityRenderer(ModEntities.LOST_BUILDER.get(), LostBuilderRenderer::new);
        event.registerEntityRenderer(ModEntities.LOST_FIGHTER.get(), LostFighterRenderer::new);
        event.registerEntityRenderer(ModEntities.LOST.get(), LostRenderer::new);
        event.registerEntityRenderer(ModEntities.LOST_FISHERMAN.get(), LostFishermanRenderer::new);
    }
}
