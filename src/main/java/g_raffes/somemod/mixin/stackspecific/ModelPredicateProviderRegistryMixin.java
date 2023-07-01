package g_raffes.somemod.mixin.stackspecific;

import g_raffes.somemod.item.api.StackSpecificItem;
import g_raffes.somemod.mixin.api.StaticHolder;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModelPredicateProviderRegistry.class)
abstract class ModelPredicateProviderRegistryMixin {
    @Redirect(
            method = "get(Lnet/minecraft/item/Item;Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/item/ModelPredicateProvider;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getMaxDamage()I")
    )
    private static int redirectGetMaxDamage(Item item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getMaxDamage(StaticHolder.stack1);
        } else {
            return item.getMaxDamage();
        }
    }
}
