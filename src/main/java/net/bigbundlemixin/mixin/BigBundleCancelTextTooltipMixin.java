package net.bigbundlemixin.mixin;

import net.bigbundlemod.block.BigBundleBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.ItemContainerContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

@Mixin(BlockItem.class)
public class BigBundleCancelTextTooltipMixin {

    @Inject(method = "appendHoverText", at = @At("HEAD"), cancellable = true)
    private void bundleTooltip(ItemStack stack, Item.TooltipContext context,
                                List<Component> tooltip, TooltipFlag flag,
                                CallbackInfo ci) {
        if (!(((BlockItem)(Object)this).getBlock() instanceof BigBundleBlock)) return;

        ItemContainerContents container = stack.get(DataComponents.CONTAINER);
        if (container == null) { ci.cancel(); return; }

        container.nonEmptyItems().forEach(s -> {
            String line = (s.getCount() > 1 ? s.getCount() + "x " : "")
                        + s.getHoverName().getString();
            tooltip.add(Component.literal("§7" + line));
        });
        ci.cancel();
    }
}
