package g_raffes.somemod.item;

import g_raffes.somemod.api.CompoundType;
import g_raffes.somemod.item.api.CompoundItem;
import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;

public class FlavouredMiningItem extends MiningToolItem implements CompoundItem, StackSpecificItem {
    private static final int DUMMY_ATTACK_DAMAGE = 0;
    private static final int DUMMY_ATTACK_SPEED = 0;
    private static final ToolMaterial DUMMY_MATERIAL = ToolMaterials.STONE;
    private static final TagKey<Block> DUMMY_EFFECTIVE_BLOCKS = BlockTags.GOATS_SPAWNABLE_ON;

    public FlavouredMiningItem() {
        super(DUMMY_ATTACK_DAMAGE, DUMMY_ATTACK_SPEED, DUMMY_MATERIAL, DUMMY_EFFECTIVE_BLOCKS, new Item.Settings());
    }

    @Override
    public ItemStack getDisplayStack() {
        return new ItemStack(Items.STONE_PICKAXE);
    }

    @Override
    public Text getName(ItemStack stack) {
        return getCompound(stack).getDefaultName();
    }

    @Override
    public float getAttackDamage(ItemStack stack) {
        /*int extraDamage = switch (getCompound(stack).style.type) {

            case DEFAULT -> 0;
            case DAMAGEABLE -> 0;
            case FOOD -> 0;
            case HELMET -> 0;
            case CHESTPLATE -> 0;
            case LEGGINGS -> 0;
            case BOOTS -> 0;
        }*/
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
