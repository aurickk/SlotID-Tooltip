package clickslot.id.tooltip.mixin.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(AbstractContainerScreen.class)
public abstract class SlotTooltipMixin<T extends AbstractContainerMenu> {

    @Shadow
    protected Slot hoveredSlot;

    @Shadow
    protected abstract T getMenu();

    @Inject(method = "renderTooltip", at = @At("HEAD"))
    private void renderEmptySlotTooltipInRenderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, CallbackInfo ci) {
        if (this.hoveredSlot != null && this.hoveredSlot.getItem().isEmpty()) {
            AbstractContainerMenu menu = this.getMenu();
            int slotId = menu.slots.indexOf(this.hoveredSlot);
            if (slotId >= 0) {
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(Component.literal("Empty Slot").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.literal("Slot ID: " + slotId).withStyle(ChatFormatting.DARK_GRAY));
                
                guiGraphics.renderComponentTooltip(
                    Minecraft.getInstance().font,
                    tooltip,
                    mouseX,
                    mouseY
                );
            }
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderEmptySlotTooltipAtTail(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (this.hoveredSlot != null && this.hoveredSlot.getItem().isEmpty()) {
            AbstractContainerMenu menu = this.getMenu();
            int slotId = menu.slots.indexOf(this.hoveredSlot);
            if (slotId >= 0) {
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(Component.literal("Empty Slot").withStyle(ChatFormatting.GRAY));
                tooltip.add(Component.literal("Slot ID: " + slotId).withStyle(ChatFormatting.DARK_GRAY));
                
                guiGraphics.renderComponentTooltip(
                    Minecraft.getInstance().font,
                    tooltip,
                    mouseX,
                    mouseY
                );
            }
        }
    }
}
