package clickslot.id.tooltip.mixin.client;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeInventoryTooltipMixin {

    @Unique
    private static Field cachedHoveredSlotField = null;
    @Unique
    private static Method cachedGetMenuMethod = null;
    @Unique
    private static boolean cacheInitialized = false;

    @Unique
    private static void initCache() {
        if (cacheInitialized) return;
        cacheInitialized = true;
        
        try {
            MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();
            
            // Get the runtime field name for hoveredSlot
            String hoveredSlotName = resolver.mapFieldName("intermediary", 
                "net.minecraft.class_465", "field_2787", "Lnet/minecraft/class_1735;");
            
            // Get the runtime method name for getMenu
            String getMenuName = resolver.mapMethodName("intermediary",
                "net.minecraft.class_465", "method_17577", "()Lnet/minecraft/class_1703;");
            
            Class<?> abstractContainerScreenClass = net.minecraft.client.gui.screens.inventory.AbstractContainerScreen.class;
            
            cachedHoveredSlotField = abstractContainerScreenClass.getDeclaredField(hoveredSlotName);
            cachedHoveredSlotField.setAccessible(true);
            
            cachedGetMenuMethod = abstractContainerScreenClass.getDeclaredMethod(getMenuName);
            cachedGetMenuMethod.setAccessible(true);
        } catch (Exception e) {
            // Fallback: try common names
            try {
                Class<?> abstractContainerScreenClass = net.minecraft.client.gui.screens.inventory.AbstractContainerScreen.class;
                
                // Try mapped names first
                for (Field f : abstractContainerScreenClass.getDeclaredFields()) {
                    if (f.getType() == Slot.class) {
                        cachedHoveredSlotField = f;
                        cachedHoveredSlotField.setAccessible(true);
                        break;
                    }
                }
                
                for (Method m : abstractContainerScreenClass.getDeclaredMethods()) {
                    if (m.getParameterCount() == 0 && AbstractContainerMenu.class.isAssignableFrom(m.getReturnType())) {
                        cachedGetMenuMethod = m;
                        cachedGetMenuMethod.setAccessible(true);
                        break;
                    }
                }
            } catch (Exception ex) {
                // Silent fail
            }
        }
    }

    @Inject(method = "getTooltipFromContainerItem", at = @At("RETURN"))
    private void appendSlotIdToCreativeTooltip(ItemStack stack, CallbackInfoReturnable<List<Component>> cir) {
        if (!stack.isEmpty()) {
            List<Component> tooltip = cir.getReturnValue();
            if (tooltip != null) {
                initCache();
                
                try {
                    if (cachedHoveredSlotField != null && cachedGetMenuMethod != null) {
                        Slot hoveredSlot = (Slot) cachedHoveredSlotField.get(this);
                        
                        if (hoveredSlot != null && !hoveredSlot.getItem().isEmpty()) {
                            AbstractContainerMenu menu = (AbstractContainerMenu) cachedGetMenuMethod.invoke(this);
                            
                            if (menu != null) {
                                int slotId = menu.slots.indexOf(hoveredSlot);
                                if (slotId >= 0) {
                                    tooltip.add(Component.empty());
                                    tooltip.add(Component.literal("Slot ID: " + slotId).withStyle(ChatFormatting.DARK_GRAY));
                                } else {
                                    int directSlotId = hoveredSlot.index;
                                    tooltip.add(Component.empty());
                                    tooltip.add(Component.literal("Slot ID: " + directSlotId).withStyle(ChatFormatting.DARK_GRAY));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    // Silent fail
                }
            }
        }
    }
}
