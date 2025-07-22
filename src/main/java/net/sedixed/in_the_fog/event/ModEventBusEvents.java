package net.sedixed.in_the_fog.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sedixed.in_the_fog.InTheFogMod;
import net.sedixed.in_the_fog.entity.ModEntities;
import net.sedixed.in_the_fog.entity.custom.*;

@Mod.EventBusSubscriber(modid = InTheFogMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.LOST_MINER.get(), LostMinerEntity.createAttributes().build());
        event.put(ModEntities.LOST.get(), LostEntity.createAttributes().build());
        event.put(ModEntities.LOST_BUILDER.get(), LostBuilderEntity.createAttributes().build());
        event.put(ModEntities.LOST_FARMER.get(), LostFarmerEntity.createAttributes().build());
        event.put(ModEntities.LOST_FIGHTER.get(), LostFighterEntity.createAttributes().build());
        event.put(ModEntities.LOST_FISHERMAN.get(), LostFishermanEntity.createAttributes().build());
        event.put(ModEntities.LOST_ADVENTURER.get(), LostAdventurerEntity.createAttributes().build());
    }
}
