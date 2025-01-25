package biochemic.tape.registry;

import biochemic.tape.TapeMod;
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
                    return new ItemStack(ItemRegistry.TAPES.get("tape_protection"));
                }

            };
        }
    }
}
