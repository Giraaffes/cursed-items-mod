package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RepairItemRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;

@Mixin(RepairItemRecipe.class)
abstract class RepairItemRecipeMixin {
    private ArrayList<ItemStack> repairItems;

    @Redirect(
            method = "craft(Lnet/minecraft/inventory/CraftingInventory;)Lnet/minecraft/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Lists;newArrayList()Ljava/util/ArrayList;")
    )
    private ArrayList<ItemStack> redirectNewArrayList() {
        repairItems = new ArrayList<>();
        return repairItems;
    }

    @Redirect(
            method = "craft(Lnet/minecraft/inventory/CraftingInventory;)Lnet/minecraft/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getMaxDamage()I")
    )
    private int redirectGetMaxDamage(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            ItemStack stack = repairItems.get(0);
            return stackSpecificItem.getMaxDamage(stack);
        } else {
            return item.getMaxDamage();
        }
    }
}
