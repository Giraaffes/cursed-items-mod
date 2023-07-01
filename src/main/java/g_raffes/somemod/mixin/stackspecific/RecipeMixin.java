package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import g_raffes.somemod.mixin.api.ItemStackHolder;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Okay, so this functionality is split into two mixins:
 * A {@link RecipesMixin class} that implements {@link ItemStackHolder} for all children such that the
 * local item stack variable in getRemainder can be used (since local captures don't work in redirects),
 * and this interface, which makes use of ItemStackHolder and adds redirects in the parent class
 */

// TODO: just use overwrite

@Mixin(Recipe.class)
interface RecipeMixin {
    @Redirect(
            method = "getRemainder(Lnet/minecraft/inventory/Inventory;)Lnet/minecraft/util/collection/DefaultedList;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;getStack(I)Lnet/minecraft/item/ItemStack;")
    )
    private ItemStack redirectGetStack(Inventory inventory, int i) {
        ItemStack stack = inventory.getStack(i);
        ((ItemStackHolder)this).setStack(stack);
        return stack;
    }

    @Redirect(
            method = "getRemainder(Lnet/minecraft/inventory/Inventory;)Lnet/minecraft/util/collection/DefaultedList;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;hasRecipeRemainder()Z")
    )
    private boolean redirectHasRemainder(Item item, Inventory inventory) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            ItemStack stack = ((ItemStackHolder)this).getStack();
            return stackSpecificItem.hasRecipeRemainder(stack);
        } else {
            return item.hasRecipeRemainder();
        }
    }

    @Redirect(
            method = "getRemainder(Lnet/minecraft/inventory/Inventory;)Lnet/minecraft/util/collection/DefaultedList;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getRecipeRemainder()Lnet/minecraft/item/Item;")
    )
    private Item redirectGetRemainder(Item item, Inventory inventory) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            ItemStack stack = ((ItemStackHolder)this).getStack();
            return stackSpecificItem.getRecipeRemainder(stack);
        } else {
            return item.getRecipeRemainder();
        }
    }
}
