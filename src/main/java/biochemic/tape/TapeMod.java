package biochemic.tape;

import biochemic.tape.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = TapeMod.MODID,
        name = TapeMod.NAME,
        version = TapeMod.VERSION,
        acceptedMinecraftVersions = TapeMod.MC_VERSION,
        dependencies = "required-after:forge@[14.23.5.2796,)",
        useMetadata = true
)
public class TapeMod {

    public static final String MODID = "tape";
    public static final String NAME = "Tape";
    public static final String VERSION = "1.0";
    public static final String MC_VERSION = "[1.12.2]";

    public static Logger logger;

    @SidedProxy(
            clientSide = "biochemic.tape.proxy.ClientProxy",
            serverSide = "biochemic.tape.proxy.ServerProxy"
    )
    public static CommonProxy proxy;

    @Mod.Instance
    public static TapeMod instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
