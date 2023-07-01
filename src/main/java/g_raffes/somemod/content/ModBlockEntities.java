package g_raffes.somemod.content;

import g_raffes.somemod.api.ContentRegistry;
import g_raffes.somemod.blockentity.ChainBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

public abstract class ModBlockEntities {
    public static final BlockEntityType<ChainBlockEntity> CHAIN_BLOCK_ENTITY = register("chain", ChainBlockEntity::new, Blocks.CHAIN);

    private static <T extends BlockEntity> BlockEntityType<T> register(String id, ContentRegistry.BlockEntityFactory<T> factory, Block... blocks) {
        return ContentRegistry.registerBlockEntityType(id, factory, blocks);
    }

    public static String hi() {
        return "Hello!";
    }
}
