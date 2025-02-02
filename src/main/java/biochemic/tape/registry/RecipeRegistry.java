package biochemic.tape.registry;

import biochemic.tape.TapeMod;
import biochemic.tape.util.Configuration;
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
        ResourceLocation patternTapesGroup = new ResourceLocation(TapeMod.MODID + ".patternTapes");
        ResourceLocation dyedTapesGroup = new ResourceLocation(TapeMod.MODID + ".dyedTapes");
        addShaped(
            "tape_blank", null, 
            new ItemStack(ItemRegistry.TAPES.get(TapeVariants.BLANK.registryName)),
            new Object[] {
                "###",
                "#S#",
                "###",
                'S', "slimeball",
                '#', "paper"
        });

        if (Configuration.enablePatterns) {
            addDyedTape("tape_defective", patternTapesGroup, "dyeWhite", "dyeBlue");
            addDyedTape("tape_guidance", patternTapesGroup, "dyeWhite", "dyeBlack");
            addDyedTape("tape_hazard", patternTapesGroup, "dyeOrange", "dyeBlack");
            addDyedTape("tape_protection", patternTapesGroup, "dyeWhite", "dyeRed");
            addDyedTape("tape_radiation", patternTapesGroup, "dyeOrange", "dyePurple");
            addDyedTape("tape_safety", patternTapesGroup, "dyeWhite", "dyeGreen");
        }
        
        if (Configuration.enableColors) {
            addDyedTape("tape_red", dyedTapesGroup, "dyeRed", "dyeRed");
            addDyedTape("tape_blue", dyedTapesGroup, "dyeBlue", "dyeBlue");
            addDyedTape("tape_yellow", dyedTapesGroup, "dyeYellow", "dyeYellow");
            addDyedTape("tape_orange", dyedTapesGroup, "dyeOrange", "dyeOrange");
            addDyedTape("tape_purple", dyedTapesGroup, "dyePurple", "dyePurple");
            addDyedTape("tape_magenta", dyedTapesGroup, "dyeMagenta", "dyeMagenta");
            addDyedTape("tape_pink", dyedTapesGroup, "dyePink", "dyePink");
            addDyedTape("tape_green", dyedTapesGroup, "dyeGreen", "dyeGreen");
            addDyedTape("tape_light_blue", dyedTapesGroup, "dyeLightBlue", "dyeLightBlue");
            addDyedTape("tape_light_gray", dyedTapesGroup, "dyeLightGray", "dyeLightGray");
            addDyedTape("tape_cyan", dyedTapesGroup, "dyeCyan", "dyeCyan");
            addDyedTape("tape_gray", dyedTapesGroup, "dyeGray", "dyeGray");
            addDyedTape("tape_black", dyedTapesGroup, "dyeBlack", "dyeBlack");
            addDyedTape("tape_brown", dyedTapesGroup, "dyeBrown", "dyeBrown");
            addDyedTape("tape_lime", dyedTapesGroup, "dyeLime", "dyeLime");
        }
        



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
