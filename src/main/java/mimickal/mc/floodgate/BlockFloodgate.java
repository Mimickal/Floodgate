package mimickal.mc.floodgate;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class BlockFloodgate extends BlockContainer {

    public static final String NAME = "floodgate";

    public BlockFloodgate() {
        super(Material.IRON);
        setUnlocalizedName(NAME);
        setRegistryName(NAME);
        setHardness(2.0f);
        setResistance(6.0f);
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFloodgate();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

}
