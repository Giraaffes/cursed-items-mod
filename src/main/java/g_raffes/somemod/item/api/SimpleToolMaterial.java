package g_raffes.somemod.item.api;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public record SimpleToolMaterial(Item repairItem, ToolMaterial parentMaterial, int durability, float miningSpeed, float attackDamage) implements ToolMaterial {
    public SimpleToolMaterial(Item repairItem, ToolMaterial parentMaterial) {
        this(repairItem, parentMaterial, parentMaterial.getDurability(), parentMaterial.getMiningSpeedMultiplier(), parentMaterial.getAttackDamage());
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return parentMaterial.getMiningLevel();
    }

    @Override
    public int getEnchantability() {
        return parentMaterial.getEnchantability();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(repairItem);
    }
}
