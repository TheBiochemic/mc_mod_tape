package biochemic.tape.registry;

import biochemic.tape.TapeMod;
import biochemic.tape.blocks.BlockTape;
import biochemic.tape.items.*;
import biochemic.tape.util.TapeVariants;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = TapeMod.MODID)
public class ItemRegistry {

    public static HashMap<String, ItemBlockTape> TAPES = new HashMap<>(10);

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        //BlockItems
        for(TapeVariants tapeVariant : TapeVariants.LEGAL_TAPE_VARIANTS) {

            ItemBlockTape blockTape = new ItemBlockTape(tapeVariant, tapeVariant.registryName);
            blockTape.setRegistryName(tapeVariant.registryName);
            TAPES.put(tapeVariant.registryName, blockTape);
        }

        event.getRegistry().registerAll(TAPES.values().toArray(new ItemBlockTape[TAPES.size()]));
    }

}
