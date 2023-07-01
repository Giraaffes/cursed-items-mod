package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GrindstoneScreenHandler.class)
abstract class GrindstoneScreenHandlerMixin {
    @Shadow @Final
    Inventory input;

    @Redirect(
            method = "updateResult()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getMaxDamage()I")
    )
    private int redirectGetMaxDamage(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            ItemStack stack = input.getStack(0);
            return stackSpecificItem.getMaxDamage(stack);
        } else {
            return item.getMaxDamage();
        }
    }
}
