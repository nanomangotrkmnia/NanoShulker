package com.nanoshulker;

import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

/**
 * Lightweight depth check for nested Shulker Boxes.
 *
 * <p>Depth is the number of Shulker levels contained inside the given stack:
 * an empty Shulker is {@code 0}, a Shulker holding an empty Shulker is
 * {@code 1}, and so on. Inserting such a stack into another Shulker adds one
 * more level, so insertion is allowed only while
 * {@code getNestingDepth(stack) < ClientConfig.MAX_NESTING_DEPTH}.
 */
public final class ShulkerNesting {
    private ShulkerNesting() {
    }

    public static boolean isShulkerBox(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        if (!(stack.getItem() instanceof BlockItem blockItem)) {
            return false;
        }
        return blockItem.getBlock() instanceof ShulkerBoxBlock;
    }

    public static int getNestingDepth(ItemStack stack) {
        if (!isShulkerBox(stack)) {
            return 0;
        }
        ContainerComponent container = stack.get(DataComponentTypes.CONTAINER);
        if (container == null) {
            return 0;
        }
        int max = 0;
        for (ItemStack inner : container.iterateNonEmpty()) {
            if (isShulkerBox(inner)) {
                int depth = 1 + getNestingDepth(inner);
                if (depth > max) {
                    max = depth;
                }
                if (max >= ClientConfig.MAX_NESTING_DEPTH) {
                    break;
                }
            }
        }
        return max;
    }

    public static boolean canNest(ItemStack stack) {
        return getNestingDepth(stack) < ClientConfig.MAX_NESTING_DEPTH;
    }
}
