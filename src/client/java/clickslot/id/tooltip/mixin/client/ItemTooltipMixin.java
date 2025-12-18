package clickslot.id.tooltip.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(AbstractContainerScreen.class)
public abstract class ItemTooltipMixin<T extends AbstractContainerMenu> {

    @Shadow
    protected Slot hoveredSlot;

    @Shadow
    protected abstract T getMenu();

    @ModifyReturnValue(method = "getTooltipFromContainerItem", at = @At("RETURN"))
    private List<Component> appendSlotIdToTooltip(List<Component> original, ItemStack stack) {
        if (this.hoveredSlot != null && !stack.isEmpty() && original != null) {
            AbstractContainerMenu menu = this.getMenu();
            int slotId = menu.slots.indexOf(this.hoveredSlot);
            if (slotId >= 0) {
                // Create a new mutable list to avoid UnsupportedOperationException on immutable lists
                List<Component> mutableTooltip = new ArrayList<>(original);
                mutableTooltip.add(Component.empty());
                mutableTooltip.add(Component.literal("Slot ID: " + slotId).withStyle(ChatFormatting.DARK_GRAY));
                return mutableTooltip;
            }
        }
        return original;
    }
}
