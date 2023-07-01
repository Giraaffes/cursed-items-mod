package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HungerManager.class)
abstract class HungerManagerMixin {
    @Redirect(
            method = "eat(Lnet/minecraft/item/Item;Lnet/minecraft/item/ItemStack;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getFoodComponent()Lnet/minecraft/item/FoodComponent;")
    )
    private FoodComponent redirectGetFoodComponent(Item item, Item itemAgain, ItemStack stack) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getFoodComponent(stack);
        } else {
            return item.getFoodComponent();
        }
    }
}
