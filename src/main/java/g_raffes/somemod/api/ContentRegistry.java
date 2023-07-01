package g_raffes.somemod.api;

import g_raffes.somemod.Mod;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ContentRegistry {
    private static final Map<Identifier, Item> ITEMS = new HashMap<>();
    private static final Map<Identifier, BlockEntityType<?>> BLOCK_ENTITIES = new HashMap<>();

    private static final Set<BlockEntityRendererEntry<? extends BlockEntity>> BLOCK_ENTITY_RENDERERS = new HashSet<>();


    public static void initialize() {
        initializeRegistry(Registry.ITEM, ITEMS);
        initializeRegistry(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITIES);
    }

    public static void initializeClient() {
        initializeBlockEntityRenderers();
    }

    private static <T> void initializeRegistry(Registry<T> registry, Map<Identifier, T> toRegister) {
        for (Map.Entry<Identifier, T> entry : toRegister.entrySet()) {
            Registry.register(registry, entry.getKey(), entry.getValue());
        }
        toRegister.clear();
    }

    private static void initializeBlockEntityRenderers() {
        for (BlockEntityRendererEntry<? extends BlockEntity> entry : BLOCK_ENTITY_RENDERERS) {
            initializeBlockEntityRenderer(entry);
        }
        BLOCK_ENTITY_RENDERERS.clear();
    }

    private static <T extends BlockEntity> void initializeBlockEntityRenderer(BlockEntityRendererEntry<T> entry) {
        BlockEntityRendererRegistry.register(entry.blockEntityType(), entry.blockEntityRendererFactory());
    }


    public static Item registerItem(String id, Item item) {
        ITEMS.put(Mod.id(id), item);
        return item;
    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String id, BlockEntityFactory<T> blockEntityFactory, Block... blocks) {
        BlockEntityType<T> blockEntityType = FabricBlockEntityTypeBuilder.create(blockEntityFactory, blocks).build();
        BLOCK_ENTITIES.put(Mod.id(id), blockEntityType);
        return blockEntityType;
    }

    public static <T extends BlockEntity> void registerBlockEntityRenderer(BlockEntityType<T> type, BlockEntityRendererFactory<T> rendererFactory) {
        BlockEntityRendererEntry<T> entry = new BlockEntityRendererEntry<>(type, rendererFactory);
        BLOCK_ENTITY_RENDERERS.add(entry);
    }


    // Local implementation for shorter name
    @FunctionalInterface
    public interface BlockEntityFactory<T extends BlockEntity> extends FabricBlockEntityTypeBuilder.Factory<T> {}

    // ???
    private record BlockEntityRendererEntry<T extends BlockEntity>(BlockEntityType<T> blockEntityType, BlockEntityRendererFactory<T> blockEntityRendererFactory) {}
}
