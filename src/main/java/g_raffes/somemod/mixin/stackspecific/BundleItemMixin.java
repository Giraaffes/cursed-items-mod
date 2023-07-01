package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BundleItem.class)
abstract class BundleItemMixin {
    @Redirect(
            method = "onStackClicked(Lnet/minecraft/item/ItemStack;Lnet/minecraft/screen/slot/Slot;Lnet/minecraft/util/ClickType;Lnet/minecraft/entity/player/PlayerEntity;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;canBeNested()Z")
    )
    private boolean redirectCanBeNested(Item item, ItemStack moveStack, Slot toSlot, ClickType clickType, PlayerEntity player) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            ItemStack toStack = toSlot.getStack();
            return stackSpecificItem.canBeNested(toStack);
        } else {
            return item.canBeNested();
        }
    }

    @Redirect(
            method = "addToBundle(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)I",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;canBeNested()Z")
    )
    private static boolean redirectCanBeNested(Item item, ItemStack bundle, ItemStack stack) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.canBeNested(stack);
        } else {
            return item.canBeNested();
        }
    }
}
