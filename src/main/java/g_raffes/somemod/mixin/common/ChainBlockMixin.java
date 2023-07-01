package g_raffes.somemod.mixin.common;

import g_raffes.somemod.blockentity.ChainBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChainBlock.class)
abstract class ChainBlockMixin implements BlockEntityProvider {
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ChainBlockEntity(pos, state);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ChainBlockEntity blockEntity = (ChainBlockEntity)world.getBlockEntity(pos);

        ItemStack hangingStack = blockEntity.getHangingItem();
        ItemStack heldStack = player.getStackInHand(Hand.MAIN_HAND);
        if (!heldStack.isEmpty() && hangingStack.isEmpty()) {
            return blockEntity.setHangingItem(player, heldStack);
        } else {
            return blockEntity.clearHangingItem();
        }
    }
}
