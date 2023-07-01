package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TradeOffer.class)
abstract class TradeOfferMixin {
    @Shadow @Final
    private ItemStack firstBuyItem;

    @Redirect(
            method = "getAdjustedFirstBuyItem()Lnet/minecraft/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getMaxCount()I")
    )
    private int redirectGetMaxCount(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getMaxCount(firstBuyItem);
        } else {
            return item.getMaxCount();
        }
    }
}
