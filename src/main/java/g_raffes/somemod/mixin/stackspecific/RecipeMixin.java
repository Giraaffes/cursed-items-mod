package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Recipe.class)
interface RecipeMixin {
    /**
     * @author G_raffes
     * @reason Simple short method - small modification
     */
    @Overwrite
        default DefaultedList<ItemStack> getRemainder(Inventory inventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            Item item = stack.getItem();

            if (item instanceof StackSpecificItem stackSpecificItem) {
                if (stackSpecificItem.hasRecipeRemainder(stack)) {
                    defaultedList.set(i, new ItemStack(stackSpecificItem.getRecipeRemainder(stack)));
                }
            } else {
                if (item.hasRecipeRemainder()) {
                    defaultedList.set(i, new ItemStack(item.getRecipeRemainder()));
                }
            }
        }

        return defaultedList;
    }
}
