package g_raffes.somemod.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

import java.util.function.Function;

@FunctionalInterface
public interface BakeJSONModelCallback {
    Event<BakeJSONModelCallback> EVENT = EventFactory.createArrayBacked(BakeJSONModelCallback.class,
        (listeners) -> (model, loader, parent, textureGetter, settings, id, hasDepth) -> {
            for (BakeJSONModelCallback listener : listeners) {
                listener.call(model, loader, parent, textureGetter, settings, id, hasDepth);
            }
        }
    );

    void call(JsonUnbakedModel model, ModelLoader loader, JsonUnbakedModel parent, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings settings, Identifier id, boolean hasDepth);
}
