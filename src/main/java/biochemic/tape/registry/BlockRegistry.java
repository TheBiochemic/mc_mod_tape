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
    public static BlockTape[] TAPES;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        IForgeRegistry<Block> registry = event.getRegistry();

        {
            TAPES = new BlockTape[7];

            TAPES[0] = (BlockTape) new BlockTape()
                    .setRegistryName("tape_blank")
                    .setUnlocalizedName(TapeMod.MODID + "." + "tape_blank");
            TAPES[1] = (BlockTape) new BlockTape()
                    .setRegistryName("tape_hazard")
                    .setUnlocalizedName(TapeMod.MODID + "." + "tape_hazard");
            TAPES[2] = (BlockTape) new BlockTape()
                    .setRegistryName("tape_protection")
                    .setUnlocalizedName(TapeMod.MODID + "." + "tape_protection");
            TAPES[3] = (BlockTape) new BlockTape()
                    .setRegistryName("tape_defective")
                    .setUnlocalizedName(TapeMod.MODID + "." + "tape_defective");
            TAPES[4] = (BlockTape) new BlockTape()
                    .setRegistryName("tape_safety")
                    .setUnlocalizedName(TapeMod.MODID + "." + "tape_safety");
            TAPES[5] = (BlockTape) new BlockTape()
                    .setRegistryName("tape_radiation")
                    .setUnlocalizedName(TapeMod.MODID + "." + "tape_radiation");
            TAPES[6] = (BlockTape) new BlockTape()
                    .setRegistryName("tape_guidance")
                    .setUnlocalizedName(TapeMod.MODID + "." + "tape_guidance");

            for(BlockTape tape : TAPES) {
                Blocks.FIRE.setFireInfo(tape, 60, 100);
                tape.setCreativeTab(CreativeTabRegistry.DEFAULT);
            }

            registry.registerAll(TAPES);
        }

    }

}
