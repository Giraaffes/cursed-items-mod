package g_raffes.somemod.mixin.stackspecific.armor;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
abstract class ArmorFeatureRendererMixin {
    private ItemStack renderStack;

    @Inject(
            method = "renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V",
            at = @At("HEAD")
    )
    private void onRenderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, EquipmentSlot armorSlot, int light, BipedEntityModel<LivingEntity> model, CallbackInfo ci) {
        renderStack = entity.getEquippedStack(armorSlot);
    }

    @Redirect(
            method = "getArmorTexture(Lnet/minecraft/item/ArmorItem;ZLjava/lang/String;)Lnet/minecraft/util/Identifier;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorItem;getMaterial()Lnet/minecraft/item/ArmorMaterial;")
    )
    private ArmorMaterial redirectGetMaterial(ArmorItem item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getArmorMaterial(renderStack);
        } else {
            return item.getMaterial();
        }
    }
}
