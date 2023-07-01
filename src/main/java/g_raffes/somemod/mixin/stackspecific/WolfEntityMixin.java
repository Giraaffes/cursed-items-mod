package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WolfEntity.class)
abstract class WolfEntityMixin {
    @Redirect(
            method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getFoodComponent()Lnet/minecraft/item/FoodComponent;")
    )
    private FoodComponent redirectGetFoodComponent(Item item, PlayerEntity player, Hand hand) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            ItemStack stack = player.getStackInHand(hand);
            return stackSpecificItem.getFoodComponent(stack);
        } else {
            return item.getFoodComponent();
        }
    }

    @Redirect(
            method = "isBreedingItem(Lnet/minecraft/item/ItemStack;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getFoodComponent()Lnet/minecraft/item/FoodComponent;")
    )
    private FoodComponent redirectGetFoodComponent(Item item, ItemStack stack) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getFoodComponent(stack);
        } else {
            return item.getFoodComponent();
        }
    }
}
