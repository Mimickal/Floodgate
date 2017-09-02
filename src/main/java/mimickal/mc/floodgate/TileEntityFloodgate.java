package mimickal.mc.floodgate;

import com.enderio.core.common.fluid.IFluidWrapper;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntityFloodgate extends TileEntity implements IFluidWrapper {

    public static final int MAX_CAPACITY = Fluid.BUCKET_VOLUME;

    private FluidStack heldFluid;

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

        //FIXME only accept full buckets of fluid. Anything else is a hassle to work with.

        int amountFilled;

        // Attempt to consume enough fluid to hit MAX_CAPACITY
        if (heldFluid == null) {
            amountFilled = Math.min(incomingFluid.amount, MAX_CAPACITY);
            heldFluid = new FluidStack(incomingFluid, amountFilled);
        }
        else if (!heldFluid.isFluidEqual(incomingFluid)) {
            amountFilled = 0;
        }
        else {
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
     *
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
     * @return
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
        BlockPos nearestFreeSpot = findNearestFreeSpot();

        if (nearestFreeSpot != null) {
            // Fill free spot with fluid source block
            Block fluidSourceBlock = heldFluid.getFluid().getBlock();
            this.worldObj.setBlockState(nearestFreeSpot, fluidSourceBlock.getDefaultState());

            // "Empty" the tank
            heldFluid = new FluidStack(heldFluid, 0);
        }
    }

    @Nullable
    private BlockPos findNearestFreeSpot() {
        BlockPos testPos = this.pos.down();

        if (this.worldObj.isAirBlock(testPos)) {
            return testPos;
        } else {
            return null;
        }
        //this.worldObj.getBlockState(testPos).getBlock().
    }

}
