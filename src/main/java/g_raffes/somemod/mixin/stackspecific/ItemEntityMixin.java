package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEntity.class)
abstract class ItemEntityMixin {
    @Shadow public abstract ItemStack getStack();

    @Redirect(
            method = "isFireImmune()Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isFireproof()Z")
    )
    private boolean redirectIsFireproof(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.isFireproof(getStack());
        } else {
            return item.isFireproof();
        }
    }

    @Redirect(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;damage(Lnet/minecraft/entity/damage/DamageSource;)Z")
    )
    private boolean redirectDamage(Item item, DamageSource source) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.damage(getStack(), source);
        } else {
            return item.damage(source);
        }
    }
}
