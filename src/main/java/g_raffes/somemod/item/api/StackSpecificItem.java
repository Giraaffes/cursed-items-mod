package g_raffes.somemod.item.api;

import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

public interface StackSpecificItem extends ItemConvertible {
    // FIXME? Incompatible with Fabric's InventoryStorage
    default int getMaxCount(ItemStack stack) {
        return asItem().getMaxCount();
    }

    default int getMaxDamage(ItemStack stack) {
        return asItem().getMaxDamage();
    }

    default boolean isSuitableFor(ItemStack stack, BlockState state) {
        return asItem().isSuitableFor(state);
    }

    @Nullable
    default Item getRecipeRemainder(ItemStack stack) {
        return asItem().getRecipeRemainder();
    }

    default boolean hasRecipeRemainder(ItemStack stack) {
        return asItem().hasRecipeRemainder();
    }

    default int getEnchantability(ItemStack stack) {
        return asItem().getEnchantability();
    }

    default Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        return asItem().getAttributeModifiers(slot);
    }

    @Nullable
    default FoodComponent getFoodComponent(ItemStack stack) {
        return asItem().getFoodComponent();
    }

    default SoundEvent getDrinkSound(ItemStack stack) {
        return asItem().getDrinkSound();
    }

    default SoundEvent getEatSound(ItemStack stack) {
        return asItem().getEatSound();
    }

    default boolean isFireproof(ItemStack stack) {
        return asItem().isFireproof();
    }

    default boolean damage(ItemStack stack, DamageSource source) {
        return asItem().damage(source);
    }

    @Nullable
    default SoundEvent getEquipSound(ItemStack stack) {
        return asItem().getEquipSound();
    }

    default boolean canBeNested(ItemStack stack) {
        return asItem().canBeNested();
    }

    default ArmorMaterial getArmorMaterial(ItemStack stack) {
        return ((ArmorItem)asItem()).getMaterial();
    }

    default int getProtection(ItemStack stack) {
        return ((ArmorItem)asItem()).getProtection();
    }

    default float getToughness(ItemStack stack) {
        return ((ArmorItem)asItem()).getToughness();
    }

    default ToolMaterial getToolMaterial(ItemStack stack) {
        return ((ToolItem)asItem()).getMaterial();
    }

    default float getAttackDamage(ItemStack stack) {
        Item item = asItem();
        if (item instanceof SwordItem) {
            return ((SwordItem)item).getAttackDamage();
        } else {
            return ((MiningToolItem)item).getAttackDamage();
        }
    }
}
