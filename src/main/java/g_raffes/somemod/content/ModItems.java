package g_raffes.somemod.content;

import g_raffes.somemod.api.ContentRegistry;
import g_raffes.somemod.item.api.CompoundItem;
import g_raffes.somemod.item.FlavouredArmorItem;
import g_raffes.somemod.item.FlavouredItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public abstract class ModItems {
    private final static Map<CompoundItem.Type, CompoundItem> baseCompoundItems = new HashMap<>();

    public static final Item DEFAULT_COMPOUND = registerCompound(
            "compound_64", CompoundItem.Type.DEFAULT,
            new FlavouredItem(false, false)
    );
    public static final Item DAMAGEABLE_COMPOUND = registerCompound(
            "damageable_compound", CompoundItem.Type.DAMAGEABLE,
            new FlavouredItem(true, false)
    );
    public static final Item FOOD_COMPOUND = registerCompound(
            "food_compound_64", CompoundItem.Type.FOOD,
            new FlavouredItem(false, true)
    );
    public static final Item HELMET_COMPOUND = registerCompound(
            "helmet_compound", CompoundItem.Type.HELMET,
            new FlavouredArmorItem(EquipmentSlot.HEAD)
    );
    public static final Item CHESTPLATE_COMPOUND = registerCompound(
            "chestplate_compound", CompoundItem.Type.CHESTPLATE,
    new FlavouredArmorItem(EquipmentSlot.CHEST)
    );
    public static final Item LEGGINGS_COMPOUND = registerCompound(
            "leggings_compound", CompoundItem.Type.LEGGINGS,
            new FlavouredArmorItem(EquipmentSlot.LEGS)
    );
    public static final Item BOOTS_COMPOUND = registerCompound(
            "boots_compound", CompoundItem.Type.BOOTS,
            new FlavouredArmorItem(EquipmentSlot.FEET)
    );

    private static Item register(String id, Item item) {
        return ContentRegistry.registerItem(id, item);
    }

    private static Item registerCompound(String id, CompoundItem.Type type, CompoundItem compoundItem) {
        baseCompoundItems.put(type, compoundItem);
        return register(id, compoundItem.asItem());
    }

    public static CompoundItem getBaseCompound(CompoundItem.Type type) {
        return baseCompoundItems.get(type);
    }

    public static String hi() {
        return "Hello!";
    }
}
