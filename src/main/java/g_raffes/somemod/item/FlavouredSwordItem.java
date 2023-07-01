package g_raffes.somemod.item;

import g_raffes.somemod.api.CompoundType;
import g_raffes.somemod.item.api.CompoundItem;
import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class FlavouredSwordItem extends SwordItem implements CompoundItem, StackSpecificItem {
    private static final ToolMaterial DUMMY_MATERIAL = ToolMaterials.STONE;
    private static final int DUMMY_ATTACK_DAMAGE = 0;
    private static final int DUMMY_ATTACK_SPEED = 0;

    public FlavouredSwordItem() {
        super(DUMMY_MATERIAL, DUMMY_ATTACK_DAMAGE, DUMMY_ATTACK_SPEED, new Item.Settings());
    }

    @Override
    public Text getName(ItemStack stack) {
        return getCompound(stack).getDefaultName();
    }

    @Override
    public ItemStack getDisplayStack() {
        return new ItemStack(Items.STONE_SWORD);
    }


    @Override
    public float getAttackDamage(ItemStack stack) {
        // TODO
        return 3 + getCompound(stack).flavour.toolMaterial.getAttackDamage();
    }

    @Override
    public ToolMaterial getToolMaterial(ItemStack stack) {
        return getCompound(stack).flavour.toolMaterial;
    }

    @Override
    public int getEnchantability(ItemStack stack) {
        return getCompound(stack).style.enchantability;
    }

    @Override
    public boolean isFireproof(ItemStack stack) {
        return getCompound(stack).flavour.fireproof;
    }

    @Override
    public boolean damage(ItemStack stack, DamageSource source) {
        return !(isFireproof(stack) && source.isFire());
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return  !(ingredient.getItem() instanceof CompoundItem)
                && getCompound(stack).flavour.vanillaItem == ingredient.getItem();
    }

    private static CompoundType getCompound(ItemStack stack) {
        return CompoundType.fromItemStack(stack);
    }
}
