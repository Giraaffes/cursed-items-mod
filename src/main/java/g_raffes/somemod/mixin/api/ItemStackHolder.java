package g_raffes.somemod.mixin.api;

import net.minecraft.item.ItemStack;

public interface ItemStackHolder {
    /**
     * For RecipesMixin and RecipeMixin
     */
    ItemStack getStack();
    void setStack(ItemStack stack);

    /**
     * For ModelOverrideListMixin and ModelPredicateProviderRegistryMixin
     */
    class Static {
        public static ItemStack stack1;
    }
}
