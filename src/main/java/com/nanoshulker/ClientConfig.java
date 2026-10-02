package com.nanoshulker;

/**
 * Client-side only toggle state for NanoShulker.
 *
 * <p>Defaults to {@code true} so nested Shulkers are allowed as soon as the
 * game starts. Mutated at runtime by {@code /nanoshulker on|off}; changes
 * take effect immediately because the Mixin reads this state on every call
 * via {@link #isEnabled()}. Never synchronized with the server and never
 * persisted.
 */
public final class ClientConfig {
    /**
     * Maximum nesting depth counted as containment edges from the outermost
     * Shulker Box. {@code 2} allows 3 boxes total: outer &gt; middle &gt; inner.
     */
    public static final int MAX_NESTING_DEPTH = 2;

    public static boolean allowNestedShulkers = true;

    private ClientConfig() {
    }

    /**
     * Cached-state helper for the {@code ShulkerBoxSlot} hot path so the Mixin
     * performs a single call and caches the result locally instead of issuing
     * repeated direct static field accesses.
     */
    public static boolean isEnabled() {
        return allowNestedShulkers;
    }

    public static void setEnabled(boolean enabled) {
        allowNestedShulkers = enabled;
    }
}
