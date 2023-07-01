package g_raffes.somemod.mixin.stackspecific.armor;

import g_raffes.somemod.item.api.StackSpecificItem;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;

@Mixin(PiglinBrain.class)
abstract class PiglinBrainMixin {
    private static ItemStack loopStack;

    @Redirect(
            method = "wearsGoldArmor(Lnet/minecraft/entity/LivingEntity;)Z",
            at = @At(value ="INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;")
    )
    private static Object redirectIteratorNext(Iterator<ItemStack> iterator) {
        loopStack = iterator.next();
        return loopStack;
    }

    @Redirect(
            method = "wearsGoldArmor(Lnet/minecraft/entity/LivingEntity;)Z",
            at = @At(value ="INVOKE", target = "Lnet/minecraft/item/ArmorItem;getMaterial()Lnet/minecraft/item/ArmorMaterial;")
    )
    private static ArmorMaterial redirectGetMaterial(ArmorItem item) {
        if (item instanceof StackSpecificItem stackSpecificItem) {
            return stackSpecificItem.getArmorMaterial(loopStack);
        } else {
            return item.getMaterial();
        }
    }
}
