package g_raffes.somemod.item.api;

import g_raffes.somemod.mixin.common.ArmorMaterialsAccessor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

// For custom overrides i guess
public record SimpleArmorMaterial(String name, Item repairItem, ArmorMaterial parentMaterial, int durabilityMultiplier, float toughness, float knockbackResistance) implements ArmorMaterial {
    public SimpleArmorMaterial(String name, Item repairItem, ArmorMaterial parentMaterial) {
        this(name, repairItem, parentMaterial, ((ArmorMaterialsAccessor)parentMaterial).getDurabilityMultiplier(), parentMaterial.getToughness(), parentMaterial.getKnockbackResistance());
    }

    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * durabilityMultiplier;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return parentMaterial.getProtectionAmount(slot);
    }

    @Override
    public int getEnchantability() {
        return parentMaterial.getEnchantability();
    }

    @Override
    public SoundEvent getEquipSound() {
        return parentMaterial.getEquipSound();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(repairItem);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
