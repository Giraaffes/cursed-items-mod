package g_raffes.somemod.item;

import g_raffes.somemod.api.CompoundType;
import g_raffes.somemod.content.compound.CompoundFlavour;
import g_raffes.somemod.content.compound.CompoundStyle;
import g_raffes.somemod.item.api.CompoundItem;
import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.*;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class FlavouredItem extends Item implements CompoundItem, StackSpecificItem {
    private static final FoodComponent DUMMY_FOOD_COMPONENT = FoodComponents.COOKIE;
    private static final int DUMMY_MAX_DAMAGE = 1;
    private static final int DUMMY_MAX_COUNT = 64;

    public FlavouredItem(boolean hasDamage, boolean isFood) {
        super(settings(hasDamage, isFood));
    }

    @Override
    public Text getName(ItemStack stack) {
        CompoundType compound = getCompound(stack);
        if (compound.is(CompoundFlavour.CHICKEN, CompoundStyle.ELYTRA)) {
            return new LiteralText("Chicken Wings");
        } else if (compound.is(CompoundFlavour.COOKED_CHICKEN, CompoundStyle.ELYTRA)) {
            return new LiteralText("Fried Chicken Wings");
        } else {
            return getCompound(stack).getDefaultName();
        }
    }

    @Override
    public ItemStack getDisplayStack() {
        return new ItemStack(Items.BARRIER);
    }


    @Override
    public int getMaxCount(ItemStack stack) {
        return getCompound(stack).style.maxCount;
    }

    // TODO: Max damage
    /*@Override
    public int getMaxDamage(ItemStack stack) {

    }*/

    @Override
    @Nullable
    public Item getRecipeRemainder(ItemStack stack) {
        return getCompound(stack).flavour.recipeRemainder;
    }

    @Override
    public boolean hasRecipeRemainder(ItemStack stack) {
        return getRecipeRemainder(stack) != null;
    }

    @Override
    public int getEnchantability(ItemStack stack) {
        return getCompound(stack).style.enchantability;
    }

    @Override
    public @Nullable FoodComponent getFoodComponent(ItemStack stack) {
        return getCompound(stack).flavour.foodComponent;
    }

    // TODO: drink and eat sounds

    @Override
    public boolean isFireproof(ItemStack stack) {
        return getCompound(stack).flavour.fireproof;
    }

    @Override
    public boolean damage(ItemStack stack, DamageSource source) {
        return !(isFireproof(stack) && source.isFire());
    }

    // All damageable CompoundItems can be repaired by their respective vanilla ingredients, as it is now
    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return  !(ingredient.getItem() instanceof CompoundItem)
                && getCompound(stack).flavour.vanillaItem == ingredient.getItem();
    }

    private static CompoundType getCompound(ItemStack stack) {
        return CompoundType.fromItemStack(stack);
    }

    // Since super must be first call
    private static Item.Settings settings(boolean hasDamage, boolean isFood) {
        Item.Settings settings = new Item.Settings();
        if (isFood) settings.food(DUMMY_FOOD_COMPONENT);
        if (hasDamage) {
            settings.maxDamage(DUMMY_MAX_DAMAGE);
        } else {
            settings.maxCount(DUMMY_MAX_COUNT);
        }
        return settings;
    }
}
