package mimickal.mc.floodgate.tileentity;

import com.enderio.core.common.fluid.IFluidWrapper;
import mimickal.mc.floodgate.Config;
import mimickal.mc.floodgate.client.gui.GuiFloodgate;
import mimickal.mc.floodgate.enums.FGState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class TileEntityFloodgate extends TileEntity implements IFluidWrapper {

    private static final int MAX_CAPACITY = Fluid.BUCKET_VOLUME;

    private FluidStack heldFluid;

    private ItemStackHandler handler;

    private FGState fgstate;

    public TileEntityFloodgate() {
        super();
        this.handler = new ItemStackHandler(2);
        this.fgstate = FGState.ON;
    }

    public FGState getFgstate() {
        return fgstate;
    }

    public void setFgstate(FGState fgstate) {
        this.fgstate = fgstate;
    }

    public void toggleFgstate() {
        fgstate = fgstate.next();
    }

    /*-----------------------------------------------------------------------*
     * IFluidWrapper impl
     *-----------------------------------------------------------------------*/

    /**
     * This is called prior to moving any fluid.
     * The return value doesn't seem to be used for anything.
     *
     * @param resource The fluid being offered to the floodgate
     * @return nothing?
     */
    @Override
    public int offer(FluidStack resource) {
        return 0;
    }

    /**
     * This is what the floodgate does when the offered fluid is actually
     * consumed.
     *
     * @param incomingFluid The fluid filling the floodgate
     * @return The amount of fluid the floodgate accepted
     */
    @Override
    public synchronized int fill(FluidStack incomingFluid) {
        if (!incomingFluid.getFluid().canBePlacedInWorld()) {
            return 0;
        }

        int amountFilled;

        // Attempt to consume enough fluid to hit MAX_CAPACITY
        if (heldFluid == null) {
            amountFilled = Math.min(incomingFluid.amount, MAX_CAPACITY);
            heldFluid = new FluidStack(incomingFluid, amountFilled);
        } else if (!heldFluid.isFluidEqual(incomingFluid)) {
            return 0;
        } else {
            if (heldFluid.amount + incomingFluid.amount >= MAX_CAPACITY) {
                amountFilled = MAX_CAPACITY - heldFluid.amount;
            } else {
                amountFilled = incomingFluid.amount;
            }

            heldFluid = new FluidStack(incomingFluid, heldFluid.amount + amountFilled);
        }

        // When we have a full tank, attempt to place a fluid source block
        if (heldFluid.amount == MAX_CAPACITY) {
            placeHeldFluid();
        }

        return amountFilled;
    }

    /**
     * The draining version of "offer". Pipes use this function to figure out
     * if they can extract any fluid from the floodgate.
     * <p>
     * The floodgate drains itself by placing fluid into the world,
     * therefore it never has any fluid available for draining.
     *
     * @return null
     */
    @Nullable
    @Override
    public FluidStack getAvailableFluid() {
        return null;
    }

    /**
     * This function contains the logic for how a floodgate responds to having
     * fluid extracted from it.
     * The floodgate should never have fluid extracted from it.
     *
     * @param resource Unused. Not sure what this is for.
     * @return null
     */
    @Nullable
    @Override
    public FluidStack drain(FluidStack resource) {
        return null;
    }

    /**
     * FIXME figure out what this actually does
     * Seriously I have no idea.
     */
    @Nonnull
    @Override
    public List<ITankInfoWrapper> getTankInfoWrappers() {
        return new ArrayList<>();
    }

    /*-----------------------------------------------------------------------*
     * Fluid placing logic
     *-----------------------------------------------------------------------*/

    private void placeHeldFluid() {
        BlockPos nearestFreeSpot = findNextFillSpot();

        if (nearestFreeSpot != null) {
            // Fill free spot with fluid source block
            Block fluidSourceBlock = heldFluid.getFluid().getBlock();
            this.worldObj.setBlockState(nearestFreeSpot, fluidSourceBlock.getDefaultState());

            // "Empty" the tank
            heldFluid = new FluidStack(heldFluid, 0);
        }
    }

    /**
     * This algorithm performs a depth-first-search to the nearest air block
     * that is connected through matching fluid source blocks. It allows an
     * arbitrary volume to be filled with fluid source blocks. By searching
     * depth-first, the volume should appear (to players) to fill naturally.
     * <p>
     * A list of visited spaces is maintained to avoid circular loops.
     * A stack is used to achieve the depth-first part of the search.
     * <p>
     * Everything is thrown out between runs to accommodate for dynamically
     * changing volumes.
     *
     * @return next air block or null (if no air block found)
     */
    @Nullable
    private BlockPos findNextFillSpot() {
        List<BlockPos> visited = new ArrayList<>();
        Stack<SearchState> searching = new Stack<>();

        searching.push(new SearchState(this.pos));

        search_next_spot:
        while (searching.size() > 0) {
            SearchState curSpot = searching.pop();
            BlockPos nextSpot;

            while ((nextSpot = curSpot.nextAdjBlock()) != null) {

                // Prevent us from flooding the universe
                if (inRange(nextSpot)) {
                    if (this.worldObj.isAirBlock(nextSpot)) {
                        return nextSpot;
                    } else if (matchesHeldFluid(nextSpot) && !visited.contains(nextSpot)) {

                        // Fluid flows only through air, so treat flowing fluid as air.
                        if (isFluidFlowing(nextSpot)) {
                            return nextSpot;
                        }

                        searching.push(curSpot);
                        searching.push(new SearchState(nextSpot));
                        visited.add(nextSpot);
                        continue search_next_spot;
                    }
                }
            }
        }

        return null;
    }

    private boolean matchesHeldFluid(BlockPos testedPos) {
        Material incomingFluidMaterial = this.worldObj.getBlockState(testedPos).getBlock().getDefaultState().getMaterial();
        Material heldFluidMaterial = this.heldFluid.getFluid().getBlock().getDefaultState().getMaterial();
        return incomingFluidMaterial == heldFluidMaterial;
    }

    private boolean inRange(BlockPos otherPos) {
        return Math.abs(pos.getX() - otherPos.getX()) <= Config.RANGE &&
                Math.abs(pos.getY() - otherPos.getY()) <= Config.RANGE &&
                Math.abs(pos.getZ() - otherPos.getZ()) <= Config.RANGE;
    }

    private boolean isFluidFlowing(BlockPos testedPos) {
        Block testedBlock = this.worldObj.getBlockState(testedPos).getBlock();

        // A block meta of 0 means it's a source block.
        // Vanilla fluids extend BlockLiquid. Mod fluids implement IFluidBlock.
        // Yes, this is horrible and should be a build-in function.
        if (testedBlock instanceof BlockLiquid || testedBlock instanceof IFluidBlock) {
            int meta = testedBlock.getMetaFromState(this.worldObj.getBlockState(testedPos));
            return meta != 0;
        }

        return false;
    }

    /**
     * This class encapsulates the concept of searching around a block.
     * Encapsulating this data allows us to save the search state at each block.
     */
    private class SearchState {
        private Queue<EnumFacing> directions;
        private BlockPos blockPos;

        private SearchState(BlockPos pos) {
            this.blockPos = pos;
            directions = new LinkedList<>(Arrays.asList(
                    EnumFacing.DOWN,
                    EnumFacing.NORTH,
                    EnumFacing.SOUTH,
                    EnumFacing.EAST,
                    EnumFacing.WEST
            ));
        }

        @Nullable
        private BlockPos nextAdjBlock() {
            EnumFacing searchDir = this.directions.poll();
            return searchDir == null ? null : this.blockPos.offset(searchDir);
        }
    }

    /**
     * Get all of the capabilities
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) this.handler;
        return super.getCapability(capability, facing);
    }

    /**
     * Say which capabilities the {@link TileEntity} has
     */
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) ||
                super.hasCapability(capability, facing);
    }

    /**
     * Says whether the player can interact with the block - used for our
     * {@link GuiFloodgate}
     *
     * @param player The player to test
     * @return If the player can interact with the block
     */
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.getPos()) == this
                && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
    }

}
