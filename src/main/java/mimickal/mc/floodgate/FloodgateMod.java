package mimickal.mc.floodgate;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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

    @SidedProxy(
            serverSide = "mimickal.mc.floodgate.CommonProxy",
            clientSide = "mimickal.mc.floodgate.ClientProxy"
    )
    public static CommonProxy proxy;

    public static BlockFloodgate floodgate;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Loading Floodgate");
        initFloodgateBlock();
        initFloodgateTileEntity();
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

}
