package g_raffes.somemod.mixin.stackspecific;

import com.google.common.collect.Multimap;
import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
abstract class ItemStackMixin {
    /*
    // TEMPLATE //

    @Redirect(
            method = "",
            at = @At(value = "INVOKE", target = "")
    )
    private  redirect(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.((ItemStack)(Object)this);
        } else {
            return item.();
        }
    }

    */

    @Redirect(
            method = "isSuitableFor(Lnet/minecraft/block/BlockState;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isSuitableFor(Lnet/minecraft/block/BlockState;)Z")
    )
    private boolean redirectIsSuitableFor(Item item, BlockState state) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.isSuitableFor((ItemStack)(Object)this, state);
        } else {
            return item.isSuitableFor(state);
        }
    }

    @Redirect(
            method = "getAttributeModifiers(Lnet/minecraft/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getAttributeModifiers(Lnet/minecraft/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;")
    )
    private Multimap<EntityAttribute, EntityAttributeModifier> redirectGetAttributeModifiers(Item item, EquipmentSlot slot) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getAttributeModifiers((ItemStack)(Object)this, slot);
        } else {
            return item.getAttributeModifiers(slot);
        }
    }

    @Redirect(
            method = "getDrinkSound()Lnet/minecraft/sound/SoundEvent;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getDrinkSound()Lnet/minecraft/sound/SoundEvent;")
    )
    private SoundEvent redirectGetDrinkSound(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getDrinkSound((ItemStack)(Object)this);
        } else {
            return item.getDrinkSound();
        }
    }

    @Redirect(
            method = "getEatSound()Lnet/minecraft/sound/SoundEvent;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getEatSound()Lnet/minecraft/sound/SoundEvent;")
    )
    private SoundEvent redirectGetEatSound(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getEatSound((ItemStack)(Object)this);
        } else {
            return item.getEatSound();
        }
    }

    @Redirect(
            method = "getEquipSound()Lnet/minecraft/sound/SoundEvent;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getEquipSound()Lnet/minecraft/sound/SoundEvent;")
    )
    private SoundEvent redirectGetEquipSound(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getEquipSound((ItemStack)(Object)this);
        } else {
            return item.getEquipSound();
        }
    }

    @Redirect(
            method = "isDamageable()Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getMaxDamage()I")
    )
    private int redirectGetMaxDamage1(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getMaxDamage((ItemStack)(Object)this);
        } else {
            return item.getMaxDamage();
        }
    }

    @Redirect(
            method = "getMaxDamage()I",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getMaxDamage()I")
    )
    private int redirectGetMaxDamage2(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getMaxDamage((ItemStack)(Object)this);
        } else {
            return item.getMaxDamage();
        }
    }

    @Redirect(
            method = "getMaxCount()I",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getMaxCount()I")
    )
    private int redirectGetMaxCount(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getMaxCount((ItemStack)(Object)this);
        } else {
            return item.getMaxCount();
        }
    }
}
