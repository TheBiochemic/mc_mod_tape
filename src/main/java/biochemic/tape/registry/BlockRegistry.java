package biochemic.tape.registry;

import biochemic.tape.TapeMod;
import biochemic.tape.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = TapeMod.MODID)
public class BlockRegistry {
    public static BlockTape TAPE;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        IForgeRegistry<Block> registry = event.getRegistry();
        
        TAPE = (BlockTape) new BlockTape()
            .setRegistryName("tape")
            .setUnlocalizedName(TapeMod.MODID + "." + "tape");

        Blocks.FIRE.setFireInfo(TAPE, 60, 100);
            registry.register(TAPE);
        
    }

}
