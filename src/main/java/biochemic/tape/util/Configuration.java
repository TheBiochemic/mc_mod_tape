package biochemic.tape.util;

import biochemic.tape.TapeMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.gui.ForgeGuiFactory.ForgeConfigGui;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = TapeMod.MODID)
public class Configuration {
    
    @Config.Comment("The max Squared Distance at which the TapeBlock is still rendering")
    public static double renderTileEntityDistance = 4096.0D;

    @Config.Comment("Enable all the Tapes, that are simple colors")
    public static boolean enableColors = true;

    @Config.Comment("Enable all the Tapes, that have a pattern")
    public static boolean enablePatterns = true;

    /*@Config.Comment("Configure recipe related stuff here")
    public static final ConfigRecipes recipes = new ConfigRecipes();
    public static class ConfigRecipes {

        @Config.Comment("Replaces the OreDict Recipe with the non OreDict one for Slime Block")
        public boolean replaceSlimeblockRecipe = true;
    }*/



    /*@Config.Comment("If you want to change Mod Compatibility, that is the right place")
    public static final ConfigCompat compatibility = new ConfigCompat();
    public static class ConfigCompat {

        @Config.Comment("Enables recipes for Immersive Engineering")
        public boolean enableImmersiveEngineering = true;
    }*/

    @Mod.EventBusSubscriber(modid = TapeMod.MODID)
    private static class Handler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(TapeMod.MODID)) {
                ConfigManager.sync(TapeMod.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
