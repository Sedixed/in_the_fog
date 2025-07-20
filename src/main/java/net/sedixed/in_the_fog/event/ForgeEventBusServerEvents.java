package net.sedixed.in_the_fog.event;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sedixed.in_the_fog.InTheFogMod;
import net.sedixed.in_the_fog.network.PacketHandler;
import net.sedixed.in_the_fog.util.FogManager;

@Mod.EventBusSubscriber(modid = InTheFogMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventBusServerEvents {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.level().isClientSide || event.phase != TickEvent.Phase.END) return;
        PacketHandler.sendFogStateToAll(FogManager.isFogActive());
    }
}
