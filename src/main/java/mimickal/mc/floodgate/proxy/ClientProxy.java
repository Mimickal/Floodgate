package mimickal.mc.floodgate.proxy;

import mimickal.mc.floodgate.Floodgate;
import mimickal.mc.floodgate.client.gui.GuiHandler;
import mimickal.mc.floodgate.init.ModBlocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        NetworkRegistry.INSTANCE.registerGuiHandler(Floodgate.instance, new GuiHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

    /**
     * Registers the renders
     */
    @Override
    public void registerRenders() {
        ModBlocks.registerRenders();
    }


}
