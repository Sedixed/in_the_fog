package net.sedixed.in_the_fog.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.sedixed.in_the_fog.util.FogManager;

import java.util.function.Supplier;

public class FogStatePacket {
    private final boolean fogEnabled;

    public FogStatePacket(boolean fogEnabled) {
        this.fogEnabled = fogEnabled;
    }

    public static void encode(FogStatePacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.fogEnabled);
    }

    public static FogStatePacket decode(FriendlyByteBuf buf) {
        return new FogStatePacket(buf.readBoolean());
    }

    public static void handle(FogStatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            FogManager.setFogActive(msg.fogEnabled);
        });
        ctx.get().setPacketHandled(true);
    }
}
