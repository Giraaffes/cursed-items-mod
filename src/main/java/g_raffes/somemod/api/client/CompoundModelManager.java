package g_raffes.somemod.api.client;

import g_raffes.somemod.Mod;
import g_raffes.somemod.api.event.BakeJSONModelCallback;
import g_raffes.somemod.api.event.LoadSpriteCallback;
import g_raffes.somemod.content.compound.CompoundFlavour;
import g_raffes.somemod.api.CompoundType;
import g_raffes.somemod.content.compound.CompoundStyle;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.impl.client.model.BakedModelManagerHooks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class CompoundModelManager {
    public static final Identifier COMPOUND_ATLAS_SPRITE = Mod.id("compound_atlas");

    private static Map<String, Map<CompoundType, Sprite>> modelHooks;
    private static Map<CompoundType, BakedModel> compoundModels;
    private static boolean listening = true;

    private static final Map<CompoundType, Identifier> overrides = new HashMap<>();

    public static void initialize() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(CompoundModelManager::registerCompoundAtlas);

        LoadSpriteCallback.EVENT.register(CompoundModelManager::onSpriteLoaded);
        BakeJSONModelCallback.EVENT.register(CompoundModelManager::onModelBake);

        ModelLoadingRegistry.INSTANCE.registerModelProvider(CompoundModelManager::onAppendModels);
    }

    @Nullable
    public static BakedModel getCompoundModel(CompoundType compound) {
        if (overrides.containsKey(compound)) {
            Identifier id = overrides.get(compound);
            return getGameModel(id);
        } else {
            return compoundModels.getOrDefault(compound, null);
        }
    }

    public static void addOverride(CompoundType compound, Identifier modelId) {
        overrides.put(compound, modelId);
    }

    private static void registerCompoundAtlas(SpriteAtlasTexture atlas, ClientSpriteRegistryCallback.Registry registry) {
        registry.register(COMPOUND_ATLAS_SPRITE);
    }

    private static void onSpriteLoaded(Identifier atlasId, Identifier spriteId, Sprite sprite, int atlasWidth, int atlasHeight) {
        if (atlasId.equals(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE) && spriteId.equals(COMPOUND_ATLAS_SPRITE)) {
            onCompoundAtlasLoaded(sprite, atlasWidth, atlasHeight);
        }
    }

    private static void onCompoundAtlasLoaded(Sprite sprite, int spriteAtlasWidth, int spriteAtlasHeight) {
        if (sprite == null) throw new RuntimeException("Compound item atlas could not be loaded");

        int styleCount = CompoundStyle.length();
        int flavoursCount = CompoundFlavour.length();
        int xSprites = sprite.getWidth()/16;
        int ySprites = sprite.getHeight()/16;

        if (xSprites < styleCount) {
            int missingCount = styleCount - xSprites;
            Mod.LOGGER.warn("Compound item atlas is too small, {} style types will not have textures", missingCount);
            styleCount = xSprites;
        } else if (ySprites < flavoursCount) {
            int missingCount = flavoursCount - ySprites;
            Mod.LOGGER.warn("Compound item atlas is too small, {} flavour types will not have textures", missingCount);
            flavoursCount = ySprites;
        }

        Sprite[][] splitAtlasSprites = splitCompoundAtlas(sprite, spriteAtlasWidth, spriteAtlasHeight);

        prepareModelHooks(splitAtlasSprites, styleCount, flavoursCount);

        compoundModels = new HashMap<>();
    }

    private static Sprite[][] splitCompoundAtlas(Sprite compoundAtlasSprite, int spriteAtlasWidth, int spriteAtlasHeight) {
        int spriteWidth = compoundAtlasSprite.getWidth();
        int spriteHeight = compoundAtlasSprite.getHeight();

        if (spriteWidth % 16 != 0 || spriteHeight % 16 != 0) {
            Mod.LOGGER.warn("Compound item atlas has an invalid size ({}x{}), must be multiple of 16", spriteWidth, spriteHeight);
        }

        // This also floors the dimensions
        int xSprites = spriteWidth/16;
        int ySprites = spriteWidth/16;

        Sprite[][] sprites = new Sprite[xSprites][ySprites];
        for (int i = 0; i < xSprites; i++) {
            for (int j = 0; j < ySprites; j++) {
                sprites[i][j] = new SubSprite(compoundAtlasSprite, spriteAtlasWidth, spriteAtlasHeight, i*16, j*16, 16, 16);
            }
        }

        return sprites;
    }

    private static void prepareModelHooks(Sprite[][] splitAtlasSprites, int styleCount, int flavourCount) {
        modelHooks = new HashMap<>();

        List<CompoundStyle> allStyles =
                Arrays.asList(CompoundStyle.values()).subList(0, styleCount);
        Map<String, List<CompoundStyle>> modelStyles =
                allStyles.stream().collect(Collectors.groupingBy((s) -> s.modelId));

        for (String modelId : modelStyles.keySet()) {
            List<CompoundStyle> styles = modelStyles.get(modelId);

            Map<CompoundType, Sprite> compoundSprites = new HashMap<>();
            for (CompoundStyle style : styles) {
                int i = style.ordinal();
                for (int j = 0; j < flavourCount; j++) {
                    CompoundFlavour flavour = CompoundFlavour.get(j);
                    CompoundType compound = CompoundType.of(style, flavour);
                    compoundSprites.put(compound, splitAtlasSprites[i][j]);
                }
            }

            modelHooks.put(modelId, compoundSprites);
        }
    }

    private static void onModelBake(JsonUnbakedModel model, ModelLoader loader, JsonUnbakedModel parent, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings settings, Identifier bakedModelId, boolean hasDepth) {
        if (listening && modelHooks.containsKey(model.id)) {
            listening = false;

            Map<CompoundType, Sprite> compoundSprites = modelHooks.get(model.id);
            for (CompoundType compound : compoundSprites.keySet()) {
                Sprite flavourSprite = compoundSprites.get(compound);

                // TODO: Models with more than one texture
                Function<SpriteIdentifier, Sprite> newTextureGetter = (spriteId) -> flavourSprite;
                BakedModel compoundModel = model.bake(loader, parent, newTextureGetter, settings, bakedModelId, hasDepth);

                compoundModels.put(compound, compoundModel);
            }

            listening = true;
        }
    }

    private static void onAppendModels(ResourceManager manager, Consumer<Identifier> consumer) {
        for (Identifier id : overrides.values()) {
            consumer.accept(id);
        }
    }

    private static BakedModel getGameModel(Identifier id) {
        return ((BakedModelManagerHooks)MinecraftClient.getInstance().getBakedModelManager()).fabric_getModel(id);
    }
}
