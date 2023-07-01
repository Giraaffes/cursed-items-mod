package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerInventory.class)
abstract class PlayerInventoryMixin {
    private ItemStack loopStack;

    @Redirect(
            method = "damageArmor(Lnet/minecraft/entity/damage/DamageSource;F[I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;get(I)Ljava/lang/Object;")
    )
    private Object redirectGet(DefaultedList<ItemStack> armor, int i) {
        loopStack = armor.get(i);
        return loopStack;
    }

    @Redirect(
            method = "damageArmor(Lnet/minecraft/entity/damage/DamageSource;F[I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isFireproof()Z")
    )
    private boolean redirectIsFireproof(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.isFireproof(loopStack);
        } else {
            return item.isFireproof();
        }
    }
}
