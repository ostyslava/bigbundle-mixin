package net.bigbundlemixin.mixin;

import net.bigbundlemod.block.BigBundleBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Cancels BigBundleBlock.appendTooltip so item names don't appear twice
 * (once as text, once as visual icons from BigBundleTooltipMixin).
 */
@Mixin(value = BigBundleBlock.class, remap = false)
public class BigBundleCancelTextTooltipMixin {

    @Inject(method = "appendTooltip", at = @At("HEAD"), cancellable = true)
    private void cancelTextTooltip(ItemStack stack, Item.TooltipContext context,
                                    List<Text> tooltip, TooltipType type,
                                    CallbackInfo ci) {
        ci.cancel();
    }
}
