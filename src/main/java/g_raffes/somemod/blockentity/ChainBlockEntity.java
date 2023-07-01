package g_raffes.somemod.blockentity;

import g_raffes.somemod.content.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class ChainBlockEntity extends BlockEntity {
    private ItemStack hangingItem;
    private float itemRotation;

    public ChainBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHAIN_BLOCK_ENTITY, pos, state);
        this.hangingItem = ItemStack.EMPTY;
        this.itemRotation = 0F;
    }

    public ActionResult setHangingItem(PlayerEntity player, ItemStack stack) {
        hangingItem = stack.split(1);
        itemRotation = player.getYaw();
        return ActionResult.CONSUME;
    }

    public ActionResult clearHangingItem() {
        if (!hangingItem.isEmpty()) {
            dropStack(hangingItem);
            hangingItem = ItemStack.EMPTY;
            itemRotation = 0F;

            return ActionResult.CONSUME;
        } else {
            return ActionResult.PASS;
        }
    }

    public ItemStack getHangingItem() {
        return hangingItem;
    }

    public float getItemRotation() {
        return itemRotation;
    }

    private ItemEntity dropStack(ItemStack stack) {
        if (getWorld() == null) {};

        // Copied from Entity.dropStack()
        ItemEntity itemEntity = new ItemEntity(getWorld(), pos.getX(), pos.getY(), pos.getZ(), stack);
        itemEntity.setToDefaultPickupDelay();
        return itemEntity;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        NbtCompound itemCompound = nbt.getCompound("hangingItem");
        hangingItem = ItemStack.fromNbt(itemCompound.getCompound("itemStack"));
        itemRotation = itemCompound.getFloat("rotation");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        NbtCompound itemCompound = new NbtCompound();
        itemCompound.put("itemStack", hangingItem.writeNbt(new NbtCompound()));
        itemCompound.putFloat("rotation", itemRotation);

        nbt.put("hangingItem", itemCompound);
    }
}
