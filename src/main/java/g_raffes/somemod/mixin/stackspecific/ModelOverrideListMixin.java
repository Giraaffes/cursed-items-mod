package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.mixin.api.StaticHolder;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelOverrideList.class)
abstract class ModelOverrideListMixin {
    @Inject(
            method = "apply(Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/entity/LivingEntity;I)Lnet/minecraft/client/render/model/BakedModel;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/item/ModelPredicateProviderRegistry;get(Lnet/minecraft/item/Item;Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/item/ModelPredicateProvider;")
    )
    private void onModelPredicateProviderGet(BakedModel model, ItemStack stack, ClientWorld world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> ci) {
        StaticHolder.stack1 = stack;
    }
}
