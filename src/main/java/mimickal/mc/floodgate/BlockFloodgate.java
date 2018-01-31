package mimickal.mc.floodgate;

import mimickal.mc.floodgate.client.gui.GuiHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

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

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            playerIn.openGui(FloodgateMod.instance, GuiHandler.Floodgate, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
