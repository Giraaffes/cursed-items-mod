package g_raffes.somemod.content.compound;

import g_raffes.somemod.item.api.CompoundItem;
import org.jetbrains.annotations.Nullable;

public enum CompoundStyle {
    HELMET("Helmet", "minecraft:item/netherite_helmet", CompoundItem.Type.HELMET),
    CHESTPLATE("Chestplate", "minecraft:item/netherite_chestplate", CompoundItem.Type.CHESTPLATE),
    LEGGINGS("Leggings", "minecraft:item/netherite_leggings", CompoundItem.Type.LEGGINGS),
    BOOTS("Boots", "minecraft:item/netherite_boots", CompoundItem.Type.BOOTS),
    CRYSTAL("Crystal", "minecraft:item/amethyst_shard", CompoundItem.Type.DEFAULT, Extra.withMaxCount(16)),
    ELYTRA("Elytra", "minecraft:item/elytra", CompoundItem.Type.DEFAULT, Extra.withMaxCount(1));


    public final String displayName;
    public final String modelId;
    public final CompoundItem.Type type;
    public final int maxCount;
    public final int enchantability;

    CompoundStyle(String displayName, String modelId, CompoundItem.Type type, Extra extra) {
        this.displayName = displayName;
        this.modelId = modelId;
        this.type = type;

        this.maxCount = extra.maxCount;
        this.enchantability = extra.enchantability;
    }

    CompoundStyle(String displayName, String modelId, CompoundItem.Type type) {
        this(displayName, modelId, type, new Extra());
    }

    public static int length() {
        return values().length;
    }

    public static CompoundStyle get(int index) {
        return values()[index];
    }


    private static class Extra {
        public int maxCount = 64;
        public int enchantability = 0;

        public static Extra withEnchantability(int setEnchantability) {
            return (new Extra()).enchantability(setEnchantability);
        }
        public Extra enchantability(int setEnchantability) {
            enchantability = setEnchantability;
            return this;
        }

        public static Extra withMaxCount(int setMaxCount) {
            return (new Extra()).maxCount(setMaxCount);
        }
        public Extra maxCount(int setMaxCount) {
            maxCount = setMaxCount;
            return this;
        }
    }
}
