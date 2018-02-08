package mimickal.mc.floodgate.init;

import mimickal.mc.floodgate.Floodgate;
import mimickal.mc.floodgate.Reference;
import mimickal.mc.floodgate.block.BlockFloodgate;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static BlockFloodgate floodgate;

    public static void init() {
        floodgate = new BlockFloodgate();
    }

    public static void register() {
        registerBlock(floodgate, new ItemBlock(floodgate));
    }

    public static void registerRenders() {
        registerItemRenderer(floodgate, 0, BlockFloodgate.NAME);
    }

    /**
     * Registers the block with a custom {@link ItemBlock}
     *
     * @param block     The block
     * @param itemBlock The {@link ItemBlock}
     */
    public static void registerBlock(Block block, ItemBlock itemBlock) {
        GameRegistry.register(block);
        GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
        Floodgate.LOGGER.info("Registered Block: " + block.getUnlocalizedName().substring(5));
    }

    /**
     * Registers the blocks renders even if it has meta data
     *
     * @param block The block
     * @param meta  The blocks meta data
     * @param id    The file name
     */
    public static void registerItemRenderer(Block block, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, id), "inventory"));
        Floodgate.LOGGER.info("Registered render for " + block.getUnlocalizedName().substring(5));
    }

}
