package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
    @Shadow protected ItemStack activeItemStack;

    @Redirect(
            method = "shouldSpawnConsumptionEffects()Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getFoodComponent()Lnet/minecraft/item/FoodComponent;")
    )
    private FoodComponent redirectGetFoodComponent(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getFoodComponent(activeItemStack);
        } else {
            return item.getFoodComponent();
        }
    }

    @Redirect(
            method = "applyFoodEffects(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getFoodComponent()Lnet/minecraft/item/FoodComponent;")
    )
    private FoodComponent redirectGetFoodComponent(Item item, ItemStack stack, World world, LivingEntity targetEntity) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getFoodComponent(stack);
        } else {
            return item.getFoodComponent();
        }
    }
}
