package biochemic.tape.proxy;

import biochemic.tape.registry.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {

        CreativeTabRegistry.registerCreativeTabs();
        TileEntityRegistry.registerTileEntities();
    }

    public void init(FMLInitializationEvent e) {
        RecipeRegistry.setupRecipes();
    }

    public void postInit(FMLPostInitializationEvent e) {}
}
