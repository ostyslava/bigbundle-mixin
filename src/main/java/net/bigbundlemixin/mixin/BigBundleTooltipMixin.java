package net.bigbundlemixin.mixin;

import net.bigbundlemod.block.BigBundleBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.inventory.tooltip.BundleTooltipData;
import net.minecraft.world.inventory.tooltip.TooltipData;
import net.minecraft.core.component.DataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Optional;

@Mixin(BlockItem.class)
public class BigBundleTooltipMixin {
    @Inject(method = "getTooltipData", at = @At("HEAD"), cancellable = true)
    private void injectBundleTooltip(ItemStack stack, CallbackInfoReturnable<Optional<TooltipData>> cir) {
        if (!(((BlockItem)(Object)this).getBlock() instanceof BigBundleBlock)) return;
        ItemContainerContents container = stack.get(DataComponents.CONTAINER);
        if (container == null) return;
        BundleContents.Mutable builder = new BundleContents.Mutable(BundleContents.EMPTY);
        container.nonEmptyItems().forEach(builder::tryInsert);
        BundleContents contents = builder.toImmutable();
        if (contents.isEmpty()) return;
        cir.setReturnValue(Optional.of(new BundleTooltipData(contents)));
    }
}
