package g_raffes.somemod.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import g_raffes.somemod.api.CompoundType;
import g_raffes.somemod.content.compound.CompoundFlavour;
import g_raffes.somemod.content.compound.CompoundStyle;
import g_raffes.somemod.item.api.CompoundItem;
import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.*;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class FlavouredArmorItem extends ArmorItem implements CompoundItem, StackSpecificItem {
    private static final ArmorMaterial DUMMY_MATERIAL = ArmorMaterials.CHAIN;

    public FlavouredArmorItem(EquipmentSlot slot) {
        super(DUMMY_MATERIAL, slot, new Item.Settings());
    }

    @Override
    public Text getName(ItemStack stack) {
        CompoundType compound = getCompound(stack);
        if (compound.is(CompoundFlavour.CHICKEN, CompoundStyle.CHESTPLATE)) {
            return new LiteralText("Chicken Breast");
        } else if (compound.is(CompoundFlavour.COOKED_CHICKEN, CompoundStyle.CHESTPLATE)) {
            return new LiteralText("Fried Chicken Breast");
        } else if (compound.is(CompoundFlavour.CHICKEN, CompoundStyle.LEGGINGS)) {
            return new LiteralText("Chicken Legs");
        } else if (compound.is(CompoundFlavour.COOKED_CHICKEN, CompoundStyle.LEGGINGS)) {
            return new LiteralText("Fried Chicken Legs");
        } else {
            return getCompound(stack).getDefaultName();
        }
    }

    @Override
    public ItemStack getDisplayStack() {
        Item displayItem = switch (slot) {
            case HEAD -> Items.CHAINMAIL_HELMET;
            case CHEST -> Items.CHAINMAIL_CHESTPLATE;
            case LEGS -> Items.CHAINMAIL_LEGGINGS;
            case FEET -> Items.CHAINMAIL_BOOTS;
            default -> Items.AIR;
        };
        return new ItemStack(displayItem);
    }


    @Override
    public ArmorMaterial getArmorMaterial(ItemStack stack) {
        return getCompound(stack).flavour.armorMaterial;
    }

    @Override
    public int getProtection(ItemStack stack) {
        return getArmorMaterial(stack).getProtectionAmount(slot);
    }

    @Override
    public float getToughness(ItemStack stack) {
        return getArmorMaterial(stack).getToughness();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return getArmorMaterial(stack).getDurability(slot);
    }

    @Override
    public int getEnchantability(ItemStack stack) {
        return getArmorMaterial(stack).getEnchantability();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot forSlot) {
        if (forSlot == slot) {
            return getCompound(stack).flavour.slotAttributeModifiers.get(forSlot);
        } else {
            return ImmutableMultimap.of();
        }
    }

    // TODO: food component + drink and eat sounds

    @Override
    public boolean isFireproof(ItemStack stack) {
        return getCompound(stack).flavour.fireproof;
    }

    @Override
    public boolean damage(ItemStack stack, DamageSource source) {
        return !(isFireproof(stack) && source.isFire());
    }

    private static CompoundType getCompound(ItemStack stack) {
        return CompoundType.fromItemStack(stack);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return getArmorMaterial(stack).getRepairIngredient().test(ingredient);
    }
}
