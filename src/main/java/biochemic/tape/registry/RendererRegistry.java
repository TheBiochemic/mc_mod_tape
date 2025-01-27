package biochemic.tape.registry;

import biochemic.tape.TapeMod;
import biochemic.tape.blocks.BlockTape;
import biochemic.tape.items.ItemBlockTape;
import biochemic.tape.renderer.TapeRenderer;
import biochemic.tape.tileentity.TileEntityTape;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TapeMod.MODID)
public class RendererRegistry {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

        for(ItemBlockTape itemTape : ItemRegistry.TAPES.values()) {
            registerModel( itemTape, 0, "inventory" );
        }
    }

    private static void registerModel(Item item, int meta, String metaName) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                new ModelResourceLocation(item.getRegistryName(), metaName));
    }

    public static void bindSpecialRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTape.class, new TapeRenderer());
    }
}
