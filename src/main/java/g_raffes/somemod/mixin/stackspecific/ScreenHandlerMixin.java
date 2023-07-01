package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(ScreenHandler.class)
abstract class ScreenHandlerMixin {
    @Redirect(
            method = "calculateStackSize(Ljava/util/Set;ILnet/minecraft/item/ItemStack;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getMaxCount()I")
    )
    private static int redirectGetMaxCount(Item item, Set<Slot> slots, int mode, ItemStack stack, int stackSize) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getMaxCount(stack);
        } else {
            return item.getMaxCount();
        }
    }
}
