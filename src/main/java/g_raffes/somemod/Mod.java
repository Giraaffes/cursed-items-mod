package g_raffes.somemod;

import g_raffes.somemod.api.Greeter;
import g_raffes.somemod.api.ContentRegistry;
import g_raffes.somemod.api.CompoundType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mod implements ModInitializer {
	public static final String MOD_ID = "somemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ItemGroup ITEM_GROUP;

	@Override
	public void onInitialize() {
		Greeter.hi();

		ContentRegistry.initialize();
		initializeItemGroup();
	}

	private static void initializeItemGroup() {
		ITEM_GROUP = FabricItemGroupBuilder.create(Mod.id("compound_items")).appendItems((itemStacks) -> {
			for (CompoundType compound : CompoundType.getOrderedTypes()) {
				itemStacks.add(compound.asItemStack());
			}
		}).build();
	}

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}
}
