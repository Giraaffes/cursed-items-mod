package g_raffes.somemod.api.client;

import g_raffes.somemod.Mod;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;

public abstract class DummySprite extends Sprite {
    private static final Identifier DUMMY = Mod.id("dummy");
    private static final NativeImage EMPTY_IMAGE = new NativeImage(16, 16, false);

    protected DummySprite(SpriteAtlasTexture atlas, int atlasWidth, int atlasHeight, int x, int y, int width, int height) {
        super(
            atlas,
            new Sprite.Info(DUMMY, width, height, AnimationResourceMetadata.EMPTY),
            0, atlasWidth, atlasHeight, x, y, EMPTY_IMAGE
        );
    }
}
