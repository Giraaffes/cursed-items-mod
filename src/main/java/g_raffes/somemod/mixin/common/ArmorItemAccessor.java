package g_raffes.somemod.mixin.common;

import net.minecraft.item.ArmorItem;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.UUID;

@Mixin(ArmorItem.class)
public interface ArmorItemAccessor {
    @Accessor("MODIFIERS")
    static UUID[] getModifiers() {
        throw new AssertionError();
    }
}
