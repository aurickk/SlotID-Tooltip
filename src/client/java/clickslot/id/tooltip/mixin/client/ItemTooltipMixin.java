package clickslot.id.tooltip.mixin.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AbstractContainerScreen.class)
public abstract class ItemTooltipMixin<T extends AbstractContainerMenu> {

    @Shadow
    protected Slot hoveredSlot;

    @Shadow
    protected abstract T getMenu();

    @Inject(method = "getTooltipFromContainerItem", at = @At("RETURN"))
    private void appendSlotIdToTooltip(ItemStack stack, CallbackInfoReturnable<List<Component>> cir) {
        if (this.hoveredSlot != null && !stack.isEmpty()) {
            List<Component> tooltip = cir.getReturnValue();
            if (tooltip != null) {
                AbstractContainerMenu menu = this.getMenu();
                int slotId = menu.slots.indexOf(this.hoveredSlot);
                if (slotId >= 0) {
                    tooltip.add(Component.empty());
                    tooltip.add(Component.literal("Slot ID: " + slotId).withStyle(ChatFormatting.DARK_GRAY));
                }
            }
        }
    }
}
