package mimickal.mc.floodgate.proxy;

import mimickal.mc.floodgate.Floodgate;
import mimickal.mc.floodgate.Reference;
import mimickal.mc.floodgate.client.gui.GuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(Floodgate.instance, new GuiHandler());
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelResourceLocation location = new ModelResourceLocation(Reference.MOD_ID + ":" + id, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, location);

        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        mesher.register(item, meta, location);
    }

}
