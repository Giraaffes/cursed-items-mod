package g_raffes.somemod.mixin.client;

import g_raffes.somemod.api.event.LoadSpriteCallback;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpriteAtlasTexture.class)
abstract class SpriteAtlasTextureMixin {
    @Shadow @Final
    private Identifier id;

    @Inject(
            method = "loadSprite(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/client/texture/Sprite$Info;IIIII)Lnet/minecraft/client/texture/Sprite;",
            at = @At("RETURN")
    )
    private void onLoadSprite(ResourceManager container, Sprite.Info info, int atlasWidth, int atlasHeight, int maxLevel, int x, int y, CallbackInfoReturnable<Sprite> ci) {
        LoadSpriteCallback.EVENT.invoker().call(id, info.getId(), ci.getReturnValue(), atlasWidth, atlasHeight);
    }
}
