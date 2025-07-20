package net.sedixed.in_the_fog.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sedixed.in_the_fog.InTheFogMod;
import net.sedixed.in_the_fog.util.FogManager;

@Mod.EventBusSubscriber(modid = InTheFogMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeEventBusClientEvents {
    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        float progress = FogManager.getFogProgress();
        if (!FogManager.shouldRenderFog()) {
            return;
        }

        float near = 0.5f;
        float far = (16.0f * 16.0f) - (246.0f * progress);

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player == null) return;

        event.setNearPlaneDistance(near); // fog start
        event.setFarPlaneDistance(far); // full fog
        event.setCanceled(true); // bypass render distance fog
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            FogManager.tick(Minecraft.getInstance());
        }
    }
}
