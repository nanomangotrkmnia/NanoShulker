package com.nanoshulker.mixin;

import com.nanoshulker.ClientConfig;
import com.nanoshulker.ShulkerNesting;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.ShulkerBoxSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Allows depth-limited Shulker Boxes inside Shulker Box slots while the
 * client-side toggle is on.
 *
 * <p>Target verified against Yarn {@code 1.21.11+build.6}:
 * {@code net.minecraft.screen.slot.ShulkerBoxSlot#canInsert(net.minecraft.item.ItemStack)}.
 * When the toggle is off, or the stack is not a Shulker Box, the injection
 * does nothing and vanilla behavior runs.
 */
@Mixin(ShulkerBoxSlot.class)
public class ShulkerBoxSlotMixin {
    @Inject(method = "canInsert(Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private void nanoshulker$allowNestedShulkers(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        final boolean enabled = ClientConfig.isEnabled();
        if (!enabled) {
            return;
        }
        if (!ShulkerNesting.isShulkerBox(stack)) {
            return;
        }
        cir.setReturnValue(ShulkerNesting.canNest(stack));
    }
}
