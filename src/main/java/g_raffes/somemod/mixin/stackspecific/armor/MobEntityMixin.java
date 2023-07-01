package g_raffes.somemod.mixin.stackspecific.armor;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MobEntity.class)
abstract class MobEntityMixin {
    // Pain

    private int stackSpecificGetProtection(ArmorItem item, ItemStack stack) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getProtection(stack);
        } else {
            return item.getProtection();
        }
    }

    private float stackSpecificGetToughness(ArmorItem item, ItemStack stack) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getToughness(stack);
        } else {
            return item.getToughness();
        }
    }

    @Redirect(
            method = "prefersNewEquipment(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorItem;getProtection()I", ordinal = 0)
    )
    private int redirectGetProtection1(ArmorItem item, ItemStack newStack, ItemStack oldStack) {
        return stackSpecificGetProtection(item, newStack);
    }

    @Redirect(
            method = "prefersNewEquipment(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorItem;getProtection()I", ordinal = 1)
    )
    private int redirectGetProtection2(ArmorItem item, ItemStack newStack, ItemStack oldStack) {
        return stackSpecificGetProtection(item, oldStack);
    }

    @Redirect(
            method = "prefersNewEquipment(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorItem;getProtection()I", ordinal = 2)
    )
    private int redirectGetProtection3(ArmorItem item, ItemStack newStack, ItemStack oldStack) {
        return stackSpecificGetProtection(item, newStack);
    }

    @Redirect(
            method = "prefersNewEquipment(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorItem;getProtection()I", ordinal = 3)
    )
    private int redirectGetProtection4(ArmorItem item, ItemStack newStack, ItemStack oldStack) {
        return stackSpecificGetProtection(item, oldStack);
    }

    @Redirect(
            method = "prefersNewEquipment(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorItem;getToughness()F", ordinal = 0)
    )
    private float redirectGetToughness1(ArmorItem item, ItemStack newStack, ItemStack oldStack) {
        return stackSpecificGetToughness(item, newStack);
    }

    @Redirect(
            method = "prefersNewEquipment(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorItem;getToughness()F", ordinal = 1)
    )
    private float redirectGetToughness2(ArmorItem item, ItemStack newStack, ItemStack oldStack) {
        return stackSpecificGetToughness(item, oldStack);
    }

    @Redirect(
            method = "prefersNewEquipment(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorItem;getToughness()F", ordinal = 2)
    )
    private float redirectGetToughness3(ArmorItem item, ItemStack newStack, ItemStack oldStack) {
        return stackSpecificGetToughness(item, newStack);
    }

    @Redirect(
            method = "prefersNewEquipment(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorItem;getToughness()F", ordinal = 3)
    )
    private float redirectGetToughness4(ArmorItem item, ItemStack newStack, ItemStack oldStack) {
        return stackSpecificGetToughness(item, oldStack);
    }
}
