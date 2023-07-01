package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(EnchantmentHelper.class)
abstract class EnchantmentHelperMixin {
    @Redirect(
            method = "calculateRequiredExperienceLevel(Ljava/util/Random;IILnet/minecraft/item/ItemStack;)I",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getEnchantability()I")
    )
    private static int redirectGetEnchantability(Item item, Random random, int slotIndex, int bookshelfCount, ItemStack stack) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getEnchantability(stack);
        } else {
            return item.getEnchantability();
        }
    }

    @Redirect(
            method = "generateEnchantments(Ljava/util/Random;Lnet/minecraft/item/ItemStack;IZ)Ljava/util/List;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getEnchantability()I")
    )
    private static int redirectGetEnchantability(Item item, Random random, ItemStack stack, int level, boolean treasureAllowed) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getEnchantability(stack);
        } else {
            return item.getEnchantability();
        }
    }
}
