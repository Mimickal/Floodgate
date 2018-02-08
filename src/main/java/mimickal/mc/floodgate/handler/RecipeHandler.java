package mimickal.mc.floodgate.handler;

import mimickal.mc.floodgate.init.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipeHandler {

    public static void registerCraftingRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.floodgate,
                "III",
                "B B",
                "IBI",
                'I', Items.IRON_INGOT,
                'B', Blocks.IRON_BARS
        ));
    }

}
