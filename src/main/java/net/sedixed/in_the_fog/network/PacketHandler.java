package net.sedixed.in_the_fog.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.sedixed.in_the_fog.InTheFogMod;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(InTheFogMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION, s -> true, s -> true
    );

    public static void register() {
        int id = 0;
        CHANNEL.registerMessage(id++, FogStatePacket.class,
                FogStatePacket::encode,
                FogStatePacket::decode,
                FogStatePacket::handle
        );
    }

    public static void sendFogStateToAll(boolean enable) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), new FogStatePacket(enable));
    }
}
