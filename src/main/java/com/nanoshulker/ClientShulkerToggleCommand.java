package com.nanoshulker;

import com.mojang.brigadier.Command;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

/**
 * Client-only entrypoint. Registers {@code /nanoshulker on|off} via the
 * Fabric client command API so no server install is ever required.
 */
public class ClientShulkerToggleCommand implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                ClientCommandManager.literal("nanoshulker")
                        .then(ClientCommandManager.literal("on").executes(context -> {
                            ClientConfig.allowNestedShulkers = true;
                            context.getSource().sendFeedback(
                                    Text.literal("[NanoShulker] Nested Shulkers enabled."));
                            return Command.SINGLE_SUCCESS;
                        }))
                        .then(ClientCommandManager.literal("off").executes(context -> {
                            ClientConfig.allowNestedShulkers = false;
                            context.getSource().sendFeedback(
                                    Text.literal("[NanoShulker] Nested Shulkers disabled."));
                            return Command.SINGLE_SUCCESS;
                        }))));
    }
}
