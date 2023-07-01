package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.ShulkerBoxSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ShulkerBoxSlot.class)
abstract class ShulkerBoxSlotMixin {
    @Redirect(
            method = "canInsert(Lnet/minecraft/item/ItemStack;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;canBeNested()Z")
    )
    private boolean redirectCanBeNested(Item item, ItemStack stack) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.canBeNested(stack);
        } else {
            return item.canBeNested();
        }
    }
}
