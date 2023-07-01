package g_raffes.somemod;

import g_raffes.somemod.api.CompoundType;
import g_raffes.somemod.api.ContentRegistry;
import g_raffes.somemod.api.client.CompoundModelManager;
import g_raffes.somemod.content.compound.CompoundFlavour;
import g_raffes.somemod.content.compound.CompoundStyle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ContentRegistry.initializeClient();
        CompoundModelManager.initialize();

        CompoundModelManager.addOverride(
                CompoundType.of(CompoundFlavour.CHICKEN, CompoundStyle.ELYTRA),
                Mod.id("item/chicken_wings")
        );
        CompoundModelManager.addOverride(
                CompoundType.of(CompoundFlavour.COOKED_CHICKEN, CompoundStyle.ELYTRA),
                Mod.id("item/fried_chicken_wings")
        );
    }
}
