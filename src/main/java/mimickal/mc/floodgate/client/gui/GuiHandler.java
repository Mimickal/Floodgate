package mimickal.mc.floodgate.client.gui;

import mimickal.mc.floodgate.container.ContainerFloodgate;
import mimickal.mc.floodgate.tileentity.TileEntityFloodgate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

    public static final int Floodgate = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == Floodgate) {
            return new ContainerFloodgate(player.inventory, (TileEntityFloodgate) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Floodgate) {
            return new GuiFloodgate(player.inventory, (TileEntityFloodgate) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
