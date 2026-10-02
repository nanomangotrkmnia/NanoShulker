package com.nanoshulker;

/**
 * Client-side only toggle state for NanoShulker.
 *
 * <p>Defaults to {@code true} so nested Shulkers are allowed as soon as the
 * game starts. Mutated at runtime by {@code /nanoshulker on|off}; changes
 * take effect immediately because the Mixin reads this field on every call.
 * Never synchronized with the server and never persisted.
 */
public final class ClientConfig {
    public static boolean allowNestedShulkers = true;

    private ClientConfig() {
    }
}
