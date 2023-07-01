package g_raffes.somemod.mixin.common;

import g_raffes.somemod.item.api.CompoundItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
abstract class ItemMixin {
    // Force overwrite of getDefaultStack()
    @Inject(
            method = "getDefaultStack()Lnet/minecraft/item/ItemStack;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onGetDefaultStack(CallbackInfoReturnable<ItemStack> ci) {
        if (this instanceof CompoundItem compoundItem) {
            ci.setReturnValue(compoundItem.getDisplayStack());
            ci.cancel();
        }
    }
}