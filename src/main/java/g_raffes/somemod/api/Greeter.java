package g_raffes.somemod.api;

import g_raffes.somemod.content.ModBlockEntities;
import g_raffes.somemod.content.ModBlockEntityRenderers;
import g_raffes.somemod.content.ModItems;

// TODO A better way to do this?

public abstract class Greeter {
    public static String hi() {
        ModItems.hi();
        ModBlockEntities.hi();
        ModBlockEntityRenderers.hi();

        return "hi!!!";
    }
}
