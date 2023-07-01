package g_raffes.somemod.content;

import g_raffes.somemod.api.ContentRegistry;
import g_raffes.somemod.blockentity.ChainBlockEntityRenderer;

public abstract class ModBlockEntityRenderers {
    static {
        ContentRegistry.registerBlockEntityRenderer(ModBlockEntities.CHAIN_BLOCK_ENTITY, ChainBlockEntityRenderer::new);
    }

    public static String hi() {
        return "Hello!";
    }
}
