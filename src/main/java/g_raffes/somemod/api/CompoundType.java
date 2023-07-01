package g_raffes.somemod.api;

import g_raffes.somemod.content.ModItems;
import g_raffes.somemod.content.compound.CompoundFlavour;
import g_raffes.somemod.content.compound.CompoundStyle;
import g_raffes.somemod.item.api.CompoundItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import javax.annotation.Nullable;

public class CompoundType {
    public static final String COMPOUND_TYPE_TAG = "CompoundType";
    public static final String STYLE_TAG = "Style";
    public static final String FLAVOUR_TAG = "Flavour";

    public CompoundStyle style;
    public CompoundFlavour flavour;

    private CompoundType(CompoundStyle style, CompoundFlavour flavour) {
        this.style = style;
        this.flavour = flavour;
    }

    public NbtCompound asNbt() {
        // NBT Compound object is not the same as a "Compound" Item
        NbtCompound nbt = new NbtCompound();
        nbt.putInt(STYLE_TAG, style.ordinal());
        nbt.putInt(FLAVOUR_TAG, flavour.ordinal());
        return nbt;
    }

    public ItemStack asItemStack() {
        CompoundItem item = ModItems.getBaseCompound(style.type);
        ItemStack stack = new ItemStack(item);
        stack.getOrCreateNbt().put(COMPOUND_TYPE_TAG, asNbt());
        return stack;
    }

    public Text getDefaultName() {
        // TODO: Language support??
        return new LiteralText(flavour.displayName + " " + style.displayName);
    }

    public boolean is(CompoundFlavour otherFlavour, CompoundStyle otherStyle) {
        return otherFlavour == flavour && otherStyle == style;
    }

    public static CompoundType of(CompoundStyle style, CompoundFlavour flavour) {
        return new CompoundType(style, flavour);
    }

    public static CompoundType of(CompoundFlavour flavour, CompoundStyle style) {
        return new CompoundType(style, flavour);
    }

    public static CompoundType fromOrdinals(int styleOrdinal, int flavourOrdinal) {
        return new CompoundType(CompoundStyle.get(styleOrdinal), CompoundFlavour.get(flavourOrdinal));
    }

    public static CompoundType fromNbt(NbtCompound nbt) {
        return CompoundType.fromOrdinals(nbt.getInt(STYLE_TAG), nbt.getInt(FLAVOUR_TAG));
    }

    @Nullable
    public static CompoundType fromItemStack(ItemStack stack) {
        if (!(stack.getItem() instanceof CompoundItem)) return null;

        NbtCompound nbt = stack.getSubNbt(COMPOUND_TYPE_TAG);
        if (nbt == null) return null;

        return fromNbt(nbt);
    }

    public static CompoundType[] getOrderedTypes() {
        int styles = CompoundStyle.values().length;
        int flavours = CompoundFlavour.values().length;

        CompoundType[] compounds = new CompoundType[styles * flavours];
        for (int i = 0; i < styles; i++) {
            for (int j = 0; j < flavours; j++) {
                compounds[j * styles + i] = fromOrdinals(i, j);
            }
        }
        return compounds;
    }

    @Override
    public int hashCode() {
        int styles = CompoundStyle.values().length;
        return flavour.ordinal() * styles + style.ordinal();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof CompoundType otherCompoundType)) {
            return false;
        } else {
            return this.style == otherCompoundType.style && this.flavour == otherCompoundType.flavour;
        }
    }
}
