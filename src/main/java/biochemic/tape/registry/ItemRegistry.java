package biochemic.tape.registry;

import biochemic.tape.TapeMod;
import biochemic.tape.blocks.BlockTape;
import biochemic.tape.items.*;
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
        for(BlockTape tape : BlockRegistry.TAPES) {

            ItemBlockTape blockTape = new ItemBlockTape(tape);
            blockTape.setRegistryName(tape.getRegistryName());
            TAPES.put(tape.getUnlocalizedName(), blockTape);
        }

        event.getRegistry().registerAll(TAPES.values().toArray(new ItemBlock[TAPES.size()]));
    }

}
