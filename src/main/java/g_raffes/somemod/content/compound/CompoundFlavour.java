package g_raffes.somemod.content.compound;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import g_raffes.somemod.item.api.SimpleArmorMaterial;
import g_raffes.somemod.item.api.SimpleToolMaterial;
import g_raffes.somemod.mixin.common.ArmorItemAccessor;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// TODO: Functionality overrides
public enum CompoundFlavour {
    COPPER("copper", "Copper", Items.COPPER_INGOT, ArmorMaterials.IRON, ToolMaterials.IRON),
    EMERALD("emerald", "Emerald", Items.EMERALD, ArmorMaterials.DIAMOND, ToolMaterials.DIAMOND),
    REDSTONE("redstone", "Redstone", Items.REDSTONE, ArmorMaterials.GOLD, ToolMaterials.STONE),
    LAPIS("lapis", "Lapis", Items.LAPIS_LAZULI, ArmorMaterials.GOLD, ToolMaterials.STONE),
    CHICKEN("chicken", "Chicken", Items.CHICKEN, ArmorMaterials.LEATHER, ToolMaterials.WOOD),
    COOKED_CHICKEN("cooked_chicken", "Fried Chicken", Items.COOKED_CHICKEN, ArmorMaterials.GOLD, ToolMaterials.STONE);


    public final String id;
    public final String displayName;
    public final Item vanillaItem;
    public final ArmorMaterial armorMaterial;
    public final Map<EquipmentSlot, Multimap<EntityAttribute, EntityAttributeModifier>> slotAttributeModifiers;
    public final ToolMaterial toolMaterial;
    @Nullable
    public final Item recipeRemainder;
    @Nullable
    public final FoodComponent foodComponent;
    public final boolean fireproof;

    CompoundFlavour(String id, String displayName, Item vanillaItem, ArmorMaterial parentArmorMaterial, ToolMaterial parentToolMaterial, Extra extra) {
        this.id = id;
        this.displayName = displayName;
        this.vanillaItem = vanillaItem;

        this.armorMaterial = new SimpleArmorMaterial(id, vanillaItem, parentArmorMaterial);
        this.slotAttributeModifiers = new HashMap<>();
        buildAttributeModifiers(EquipmentSlot.HEAD, this.armorMaterial);
        buildAttributeModifiers(EquipmentSlot.CHEST, this.armorMaterial);
        buildAttributeModifiers(EquipmentSlot.LEGS, this.armorMaterial);
        buildAttributeModifiers(EquipmentSlot.FEET, this.armorMaterial);

        this.toolMaterial = new SimpleToolMaterial(vanillaItem, parentToolMaterial);

        this.recipeRemainder = extra.recipeRemainder;
        this.foodComponent = extra.foodComponent;
        this.fireproof = extra.fireproof;
    }

    CompoundFlavour(String id, String displayName, Item vanillaItem, ArmorMaterial parentArmorMaterial, ToolMaterial parentToolMaterial) {
        this(id, displayName, vanillaItem, parentArmorMaterial, parentToolMaterial, new Extra());
    }

    public static int length() {
        return values().length;
    }

    public static CompoundFlavour get(int index) {
        return values()[index];
    }


    private void buildAttributeModifiers(EquipmentSlot slot, ArmorMaterial material) {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = ArmorItemAccessor.getModifiers()[slot.getEntitySlotId()];

        builder.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(
                uuid, "Armor modifier",
                material.getProtectionAmount(slot),
                EntityAttributeModifier.Operation.ADDITION
        ));

        builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(
                uuid, "Armor toughness",
                material.getToughness(),
                EntityAttributeModifier.Operation.ADDITION
        ));

        if (material == ArmorMaterials.NETHERITE
            || (material instanceof SimpleArmorMaterial simpleArmorMaterial
                && simpleArmorMaterial.parentMaterial() == ArmorMaterials.NETHERITE)
        ) {
            builder.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(
                    uuid, "Armor knockback resistance",
                    material.getKnockbackResistance(),
                    EntityAttributeModifier.Operation.ADDITION
            ));
        }

        Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers = builder.build();
        slotAttributeModifiers.put(slot, attributeModifiers);
    }

    private static class Extra {
        @Nullable
        public Item recipeRemainder = null;
        @Nullable
        public FoodComponent foodComponent = null;
        public boolean fireproof = false;

        public static Extra withRecipeRemainder(Item setRecipeRemainder) {
            return (new Extra()).recipeRemainder(setRecipeRemainder);
        }
        public Extra recipeRemainder(Item setRecipeRemainder) {
            recipeRemainder = setRecipeRemainder;
            return this;
        }

        public static Extra withFoodComponent(FoodComponent setFoodComponent) {
            return (new Extra()).foodComponent(setFoodComponent);
        }
        public Extra foodComponent(FoodComponent setFoodComponent) {
            foodComponent = setFoodComponent;
            return this;
        }


        public static Extra withFireproof(boolean setFireproof) {
            return (new Extra()).fireproof(setFireproof);
        }
        public Extra fireproof(boolean setFireproof) {
            fireproof = setFireproof;
            return this;
        }
    }
}
