package mimickal.mc.floodgate;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = FloodgateMod.MOD_ID,
        name = FloodgateMod.NAME,
        version = FloodgateMod.VERSION,
        acceptedMinecraftVersions = "[1.10.2]"
)
public class FloodgateMod {

    public static final String MOD_ID = "floodgate";
    public static final String NAME = "Floodgate";
    public static final String VERSION = "1.10.2-1.0.0";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @SidedProxy(
            serverSide = "mimickal.mc.floodgate.CommonProxy",
            clientSide = "mimickal.mc.floodgate.ClientProxy"
    )
    public static CommonProxy proxy;

    public static BlockFloodgate floodgate;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Loading " + NAME);
        Config.load();
        initFloodgateBlock();
        initFloodgateTileEntity();
        initFloodgateRecipe();
    }

    private void initFloodgateBlock() {
        BlockFloodgate block = new BlockFloodgate();

        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());

        GameRegistry.register(block);
        GameRegistry.register(itemBlock);
        proxy.registerItemRenderer(itemBlock, 0, BlockFloodgate.NAME);

        floodgate = block;
    }

    private void initFloodgateTileEntity() {
        GameRegistry.registerTileEntity(TileEntityFloodgate.class, MOD_ID + "_tile_entity");
    }

    private void initFloodgateRecipe() {
        GameRegistry.addRecipe(new ShapedOreRecipe(floodgate,
            "III",
            "B B",
            "IBI",
            'I', Items.IRON_INGOT,
            'B', Blocks.IRON_BARS
        ));
    }

}
