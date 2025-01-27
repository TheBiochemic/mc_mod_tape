package biochemic.tape.registry;

import biochemic.tape.TapeMod;
import biochemic.tape.util.TapeVariants;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.GameData;

public class RecipeRegistry {
    
    public static void setupRecipes() {
        ResourceLocation rawTapesGroup = new ResourceLocation(TapeMod.MODID + ".rawTapes");
        addShaped(
            "tape_blank", rawTapesGroup, 
            new ItemStack(ItemRegistry.TAPES.get(TapeVariants.BLANK.registryName)),
            new Object[] {
                "###",
                "#S#",
                "###",
                'S', "slimeball",
                '#', "paper"
        });

        addDyedTape("tape_defective", rawTapesGroup, "dyeWhite", "dyeBlue");
        addDyedTape("tape_guidance", rawTapesGroup, "dyeWhite", "dyeBlack");
        addDyedTape("tape_hazard", rawTapesGroup, "dyeOrange", "dyeBlack");
        addDyedTape("tape_protection", rawTapesGroup, "dyeWhite", "dyeRed");
        addDyedTape("tape_radiation", rawTapesGroup, "dyeOrange", "dyePurple");
        addDyedTape("tape_safety", rawTapesGroup, "dyeWhite", "dyeGreen");

    }

    private static void addShaped(String name, ResourceLocation group, ItemStack result, Object[] recipeTemplate) {
            IRecipe recipe = new ShapedOreRecipe(group, result, recipeTemplate);
            recipe.setRegistryName(new ResourceLocation(TapeMod.MODID, name));
            GameData.register_impl(recipe);
    }

    private static void addDyedTape(String name, ResourceLocation group, String dye1, String dye2) {
        addShaped(
            name, group, 
            new ItemStack(ItemRegistry.TAPES.get(name)),
            new Object[] {
                "ABA",
                "B#B",
                "ABA",
                '#', ItemRegistry.TAPES.get("tape_blank"),
                'A', dye1,
                'B', dye2
            });
    }

}
