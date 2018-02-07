package mimickal.mc.floodgate;

import mimickal.mc.floodgate.block.BlockFloodgate;
import mimickal.mc.floodgate.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.NAME,
        version = Reference.VERSION,
        acceptedMinecraftVersions = "[1.10.2]",
        dependencies = "required-after:endercore@[0.4.1.66-beta,)"
)
public class Floodgate {

    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    @Mod.Instance(Reference.MOD_ID)
    public static Floodgate instance;

    /**
     * Proxy so that we register the correct things on server and client side.
     * Client side handles the model bakery Server side handles tile entities
     * and world generation
     */
    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    public static BlockFloodgate floodgate;

    /**
     * Called first. Should initialize everything and register everything
     * @param event The event (you probably wont use this)
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    /**
     * Called to register recipes and events
     * @param event The event (you probably wont use this)
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        LOGGER.info("Loading " + Reference.NAME);
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

    /**
     * Called after everything. Should be used for mod integration
     * @param event The event (you probably wont use this)
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

}
