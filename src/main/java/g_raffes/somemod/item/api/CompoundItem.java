package g_raffes.somemod.item.api;

import net.minecraft.item.*;

// Functionality in ItemMixin
public interface CompoundItem extends ItemConvertible {
    // TODO: Dedicated stack for display or implement custom item models? (Or use some placeholder vanilla item)
    ItemStack getDisplayStack();

    enum Type {
        DEFAULT,
        DAMAGEABLE,
        FOOD,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS;
    }
}
