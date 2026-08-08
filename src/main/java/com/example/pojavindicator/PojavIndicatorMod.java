package com.example.pojavindicator;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Formatting;

import java.nio.charset.StandardCharsets;

public class PojavIndicatorMod implements ModInitializer {

    private static final String POJAV_PREFIX = "📱 [Pojav]";
    private static final String PC_PREFIX = "💻 [PC]";

    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(
            Identifier.of("minecraft", "brand"),
            (server, player, handler, buf, responseSender) -> {
                String brand = buf.readString(StandardCharsets.UTF_8).toLowerCase();

                server.execute(() -> {
                    boolean isPojav = brand.contains("pojav") || brand.contains("mojo") || brand.contains("droidbridge");

                    Text newDisplayName;
                    if (isPojav) {
                        newDisplayName = Text.literal(POJAV_PREFIX + " ")
                                .formatted(Formatting.GOLD)
                                .append(player.getName());
                    } else {
                        newDisplayName = Text.literal(PC_PREFIX + " ")
                                .formatted(Formatting.AQUA)
                                .append(player.getName());
                    }

                    player.setCustomName(newDisplayName);
                });
            }
        );
    }
}
