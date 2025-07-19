package net.sedixed.in_the_fog.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sedixed.in_the_fog.InTheFogMod;
import net.sedixed.in_the_fog.util.FogManager;

@Mod.EventBusSubscriber(modid = InTheFogMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeEventBusClientEvent {
    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        float progress = FogManager.getFogProgress();
        if (!FogManager.shouldRenderFog()) return;

        float near = 0.0f;
        float far = (16.0f * 16.0f) - (246.0f * progress);  // Passe de 48 à 12 blocs au fur et à mesure

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player == null) return;

        BlockPos center = new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ());
        int radius = 20;

        if (player.blockPosition().closerThan(center, radius)) {
            event.setNearPlaneDistance(near); // fog start
            event.setFarPlaneDistance(far); // full fog
            event.setCanceled(true); // bypass render distance fog
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            FogManager.tick(Minecraft.getInstance());
        }
    }
}
