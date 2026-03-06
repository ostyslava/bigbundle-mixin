package net.bigbundlemixin.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.ItemContainerContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

@Mixin(targets = "net.bigbundlemod.block.BigBundleBlock", remap = false)
public abstract class BigBundleCancelTextTooltipMixin extends Block {

    protected BigBundleCancelTextTooltipMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Inject(method = "appendTooltip", at = @At("HEAD"), cancellable = true)
    private void replaceTooltip(ItemStack stack, Object context,
                                 List<Component> tooltip, Object flag,
                                 CallbackInfo ci) {
        ItemContainerContents container = stack.get(DataComponents.CONTAINER);
        if (container != null) {
            container.nonEmptyItems().forEach(s -> {
                String line = (s.getCount() > 1 ? s.getCount() + "x " : "") + s.getHoverName().getString();
                tooltip.add(Component.literal(line));
            });
        }
        ci.cancel();
    }
}
