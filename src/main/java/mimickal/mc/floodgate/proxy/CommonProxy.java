package mimickal.mc.floodgate.proxy;

import mimickal.mc.floodgate.Reference;
import mimickal.mc.floodgate.tileentity.TileEntityFloodgate;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {

    }

    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {

    }

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityFloodgate.class, Reference.MOD_ID + ":floodgate");
    }

    /**
     * Registers the renders - refer to the
     * {@link ClientProxy#registerRenders()}
     */
    public void registerRenders() {

    }
}