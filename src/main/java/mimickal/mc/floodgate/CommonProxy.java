package mimickal.mc.floodgate;

import net.minecraft.item.Item;

public class CommonProxy {

    public void registerItemRenderer(Item item, int meta, String id) {
        // Noop on server
    }

}
