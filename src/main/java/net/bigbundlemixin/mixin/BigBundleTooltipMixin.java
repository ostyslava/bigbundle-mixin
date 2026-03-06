package net.bigbundlemixin.mixin;

import net.bigbundlemod.block.BigBundleBlock;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.BundleTooltipData;
import net.minecraft.item.tooltip.TooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Makes the BigBundle block item show visual item icons in its tooltip,
 * exactly like a vanilla Bundle — but showing all 4 slots.
 *
 * Strategy:
 *   1. Intercept BlockItem.getTooltipData() for BigBundle items
 *   2. Read the minecraft:container component (already copied by loot table)
 *   3. Build a BundleContentsComponent from those items
 *   4. Return BundleTooltipData so Minecraft renders the icon grid
 */
@Mixin(BlockItem.class)
public abstract class BigBundleTooltipMixin {

    @Shadow
    public abstract Block getBlock();

    @Inject(method = "getTooltipData", at = @At("HEAD"), cancellable = true)
    private void injectBundleTooltip(ItemStack stack,
                                      CallbackInfoReturnable<Optional<TooltipData>> cir) {
        // Only apply to BigBundle items
        if (!(getBlock() instanceof BigBundleBlock)) return;

        // Read the container component that the loot table copies onto the dropped item
        ContainerComponent container = stack.get(DataComponentTypes.CONTAINER);
        if (container == null) return;

        // Build a BundleContentsComponent so Minecraft can render the icon grid
        BundleContentsComponent.Builder builder =
                new BundleContentsComponent.Builder(BundleContentsComponent.EMPTY);

        container.stream()
                .filter(s -> !s.isEmpty())
                .forEach(builder::add);

        BundleContentsComponent contents = builder.build();
        if (contents.isEmpty()) return;

        cir.setReturnValue(Optional.of(new BundleTooltipData(contents)));
    }
}
