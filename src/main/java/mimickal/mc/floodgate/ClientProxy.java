package mimickal.mc.floodgate;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {

    public void registerItemRenderer(Item item, int meta, String id) {
        ModelResourceLocation location = new ModelResourceLocation(FloodgateMod.MOD_ID + ":" + id);
        ModelLoader.setCustomModelResourceLocation(item, meta, location);
    }

}
