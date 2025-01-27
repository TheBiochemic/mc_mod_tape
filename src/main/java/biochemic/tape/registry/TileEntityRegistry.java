package biochemic.tape.registry;

import biochemic.tape.TapeMod;
import biochemic.tape.tileentity.TileEntityTape;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {

    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityTape.class, new ResourceLocation(TapeMod.MODID, "tile_entity_tape"));
    }
    
}
