package mimickal.mc.floodgate;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Stack;

public class FloodgateSearchHelper {

    private static final int SEARCH_DISTANCE_LIMIT = 10; //64;

    private World world;
    private FluidStack heldFluid;
    private BlockPos currentPos;
    private Stack<SearchState> previousStates;

    public FloodgateSearchHelper(BlockPos startPos, FluidStack fluidIn, World worldIn) {
        this.world = worldIn;
        this.heldFluid = fluidIn;
        this.currentPos = startPos;

        previousStates = new Stack<>();

        previousStates.push(new SearchAround());
        previousStates.push(new SearchDown());
    }

    @Nullable
    public BlockPos nextSpot() {
        // FIXME remember to count steps and not exceed search distance!
        return null;
    }

    private SpotType testSpot(BlockPos position) {
        IBlockState blockState = world.getBlockState(position);

        if (world.isAirBlock(position)) {
            return SpotType.FREE;
        } else if (matchesHeldFluid(blockState)) {
            if (isSourceBlock(blockState)) {
                return SpotType.SOURCE;
            } else {
                return SpotType.FREE;
            }
        } else {
            return SpotType.WALL;
        }
    }

    // Assumes incoming IBlockState belongs to a fluid
    private boolean isSourceBlock(IBlockState fluidState) {
        return fluidState.getBlock().getMetaFromState(fluidState) == 0;
    }

    private boolean matchesHeldFluid(IBlockState fluidState) {
        Material incomingFluidMaterial = fluidState.getBlock().getDefaultState().getMaterial();
        Material heldFluidMaterial = heldFluid.getFluid().getBlock().getDefaultState().getMaterial();
        return incomingFluidMaterial == heldFluidMaterial;
    }

    private enum SpotType {
        FREE, SOURCE, WALL
    }

    private abstract class SearchState {
        public abstract BlockPos nextSpot(BlockPos currentSpot);
    }

    private class SearchDown extends SearchState {
        @Override
        public BlockPos nextSpot(BlockPos currentSpot) {
            return null; // TODO implement
        }
    }

    private class SearchAround extends SearchState {
        @Override
        public BlockPos nextSpot(BlockPos currentSpot) {
            return null; // TODO implement
        }
    }

}
