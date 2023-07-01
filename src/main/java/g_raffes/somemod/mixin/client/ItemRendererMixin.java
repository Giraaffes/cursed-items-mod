package g_raffes.somemod.mixin.client;

import g_raffes.somemod.api.client.CompoundModelManager;
import g_raffes.somemod.api.CompoundType;
import g_raffes.somemod.item.api.CompoundItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// TODO? Generally, add comments to mixin methods

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
abstract class ItemRendererMixin {
    @Redirect(
            method = "getModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)Lnet/minecraft/client/render/model/BakedModel;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemModels;getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/BakedModel;")
    )
    private BakedModel redirectGetModel(ItemModels models, ItemStack stack) {
        if (stack.getItem() instanceof CompoundItem) {
            CompoundType type = CompoundType.fromItemStack(stack);
            if (type != null) {
                BakedModel model = CompoundModelManager.getCompoundModel(type);
                if (model != null) return model;
            }
        }
        return models.getModel(stack);
    }
}
