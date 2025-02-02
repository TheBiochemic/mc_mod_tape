package biochemic.tape.registry;

import biochemic.tape.TapeMod;
import biochemic.tape.items.*;
import biochemic.tape.util.TapeVariants;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = TapeMod.MODID)
public class ItemRegistry {

    public static HashMap<String, ItemTape> TAPES = new HashMap<>(10);

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        //Tape Items
        for(TapeVariants tapeVariant : TapeVariants.getLegalTapeVariants()) {
            ItemTape blockTape = new ItemTape(tapeVariant, tapeVariant.registryName);
            blockTape.setRegistryName(tapeVariant.registryName);
            TAPES.put(tapeVariant.registryName, blockTape);
            event.getRegistry().register(blockTape);
        }
    }

}
