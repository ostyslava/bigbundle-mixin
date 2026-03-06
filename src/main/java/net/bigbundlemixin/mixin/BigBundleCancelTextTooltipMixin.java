package net.bigbundlemixin.mixin;

import net.bigbundlemod.block.BigBundleBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

@Mixin(value = BigBundleBlock.class, remap = false)
public class BigBundleCancelTextTooltipMixin {
    @Inject(method = "appendTooltip", at = @At("HEAD"), cancellable = true)
    private void cancelTextTooltip(ItemStack stack, Item.TooltipContext context,
                                    List<Component> tooltip, TooltipFlag type,
                                    CallbackInfo ci) {
        ci.cancel();
    }
}
