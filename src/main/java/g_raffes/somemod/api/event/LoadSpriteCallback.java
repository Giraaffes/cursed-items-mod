package g_raffes.somemod.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;

@FunctionalInterface
public interface LoadSpriteCallback {
    Event<LoadSpriteCallback> EVENT = EventFactory.createArrayBacked(LoadSpriteCallback.class,
            (listeners) -> (atlasId, id, sprite, atlasWidth, atlasHeight) -> {
                for (LoadSpriteCallback listener : listeners) {
                    listener.call(atlasId, id, sprite, atlasWidth, atlasHeight);
                }
            }
    );

    void call(Identifier atlasId, Identifier id, @Nullable Sprite sprite, int atlasWidth, int atlasHeight);
}
