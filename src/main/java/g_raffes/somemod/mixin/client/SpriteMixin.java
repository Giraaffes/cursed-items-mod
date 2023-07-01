package g_raffes.somemod.mixin.client;

import g_raffes.somemod.api.client.DummySprite;
import net.minecraft.client.texture.MipmapHelper;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Sprite.class)
abstract class SpriteMixin {
    @Shadow
    private Sprite.Animation createAnimation(Sprite.Info info, int nativeImageWidth, int nativeImageHeight, int maxLevel) {
        return null;
    }

    @Redirect(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/Sprite;createAnimation(Lnet/minecraft/client/texture/Sprite$Info;III)Lnet/minecraft/client/texture/Sprite$Animation;")
    )
    private Sprite.Animation redirectCreateAnimation(Sprite instance, Sprite.Info info, int nativeImageWidth, int nativeImageHeight, int maxLevel) {
        if ((Sprite)(Object)this instanceof DummySprite) {
            return null;
        } else {
            return createAnimation(info, nativeImageWidth, nativeImageHeight, maxLevel);
        }
    }

    @Redirect(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/MipmapHelper;getMipmapLevelsImages(Lnet/minecraft/client/texture/NativeImage;I)[Lnet/minecraft/client/texture/NativeImage;")
    )
    private NativeImage[] redirectGetMipmapLevelsImagesn(NativeImage image, int mipmap) {
        if ((Sprite)(Object)this instanceof DummySprite) {
            return new NativeImage[0];
        } else {
            return MipmapHelper.getMipmapLevelsImages(image, mipmap);
        }
    }
}
