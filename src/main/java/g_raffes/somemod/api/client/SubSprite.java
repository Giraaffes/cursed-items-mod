package g_raffes.somemod.api.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.Sprite;

@Environment(EnvType.CLIENT)
public class SubSprite extends DummySprite {
    public SubSprite(Sprite parent, int atlasWidth, int atlasHeight, int x, int y, int width, int height) {
        super(
            parent.getAtlas(),
            atlasWidth, atlasHeight, parent.getX() + x, parent.getY() + y, width, height);
    }
}
