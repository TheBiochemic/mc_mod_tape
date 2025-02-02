package biochemic.tape.registry;

import biochemic.tape.TapeMod;
import biochemic.tape.util.Configuration;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CreativeTabRegistry {
    public static CreativeTabs DEFAULT;
    public static void registerCreativeTabs() {
        {
            DEFAULT = new CreativeTabs(TapeMod.MODID) {

                @Override
                public ItemStack getTabIconItem() {
                    if (Configuration.enablePatterns) {
                        return new ItemStack(ItemRegistry.TAPES.get("tape_protection"));
                    } else if (Configuration.enableColors) {
                        return new ItemStack(ItemRegistry.TAPES.get("tape_purple"));
                    } else {
                        return new ItemStack(ItemRegistry.TAPES.get("tape_blank"));
                    }
                }
            };
        }
    }
}
