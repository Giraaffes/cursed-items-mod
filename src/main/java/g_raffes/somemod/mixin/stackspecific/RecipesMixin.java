package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.mixin.api.ItemStackHolder;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({AbstractCookingRecipe.class, CuttingRecipe.class, SmithingRecipe.class, SpecialCraftingRecipe.class, ShapedRecipe.class, ShapelessRecipe.class})
abstract class RecipesMixin implements ItemStackHolder {
    private ItemStack currentStack;

    @Override
    public ItemStack getStack() {
        return currentStack;
    }

    @Override
    public void setStack(ItemStack stack) {
        currentStack = stack;
    }
}
