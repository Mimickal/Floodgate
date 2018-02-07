package mimickal.mc.floodgate;

import mimickal.mc.floodgate.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = FloodgateMod.MOD_ID,
        name = FloodgateMod.NAME,
        version = FloodgateMod.VERSION,
        acceptedMinecraftVersions = "[1.10.2]",
        dependencies = "required-after:endercore@[0.4.1.66-beta,)"
)
public class FloodgateMod {

    // NOTE: Info duplicated in mcmod.info and build.gradle
    public static final String MOD_ID = "floodgate";
    public static final String NAME = "Floodgate";
    public static final String VERSION = "1.10.2-1.0.0";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Mod.Instance(FloodgateMod.MOD_ID)
    public static FloodgateMod instance;

    @SidedProxy(
            serverSide = "mimickal.mc.floodgate.proxy.CommonProxy",
            clientSide = "mimickal.mc.floodgate.proxy.ClientProxy"
    )
    public static CommonProxy proxy;

    public static BlockFloodgate floodgate;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        LOGGER.info("Loading " + NAME);
        Config.load();
        initFloodgateBlock();
        initFloodgateRecipe();
        proxy.registerTileEntities();
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
