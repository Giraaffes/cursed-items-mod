package g_raffes.somemod.mixin.common;

import net.minecraft.item.ArmorMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ArmorMaterials.class)
public interface ArmorMaterialsAccessor {
    @Accessor
    int getDurabilityMultiplier();
}
