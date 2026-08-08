package com.example.pojavindicator;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PojavIndicatorMod implements ModInitializer {
    public static final String MOD_ID = "pojav_indicator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public record PojavPacket(String data) implements CustomPayload {
        public static final CustomPayload.Id<PojavPacket> ID = new CustomPayload.Id<>(Identifier.of("pojav", "indicator"));
        public static final PacketCodec<RegistryByteBuf, PojavPacket> CODEC = PacketCodec.ofStatic(
            (buf, value) -> buf.writeString(value.data()),
            buf -> new PojavPacket(buf.readString())
        );

        @Override
        public CustomPayload.Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Pojav Indicator initializing for Minecraft 1.21!");

        PayloadTypeRegistry.playC2S().register(PojavPacket.ID, PojavPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(PojavPacket.ID, (payload, context) -> {
            LOGGER.info("Received Pojav signal from player: " + context.player().getName().getString());
        });
    }
}
