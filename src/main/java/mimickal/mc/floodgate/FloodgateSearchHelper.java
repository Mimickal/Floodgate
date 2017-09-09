package mimickal.mc.floodgate;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FloodgateSearchHelper {

    private static final int SEARCH_DISTANCE_LIMIT = 5; //64;

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

        // TODO uncomment this when once down searching is implemented
        // previousStates.push(new SearchDown());
    }

    @Nullable
    public BlockPos nextSpot() {
        // TODO remember to count steps and not exceed search distance!

        SearchAround searcher = new SearchAround();
        return searcher.nextSpot(currentPos);
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
        @Nullable
        public abstract BlockPos nextSpot(BlockPos currentSpot);
    }

    private class SearchDown extends SearchState {
        @Override
        @Nullable
        public BlockPos nextSpot(BlockPos currentSpot) {
            return null; // TODO implement
        }
    }


    /**
     * Searches the perimeter around the given position for an open spot.
     * Traverses matching fluid source blocks to get to the edge of existing
     * pools of fluid.
     *
     * If a free spot is found, return it immediately.
     * If a source block (but no free spot) is found, expand perimeter and search again.
     * If no free or source blocks found, return null.
     */
    private class SearchAround extends SearchState {
        @Override
        @Nullable
        public BlockPos nextSpot(BlockPos centerPos) {
            boolean foundSource = false;
            int radius = 1;

            do {
                // Generate list of positions to check
                List<BlockPos> checkSpots = new ArrayList<>();
                for (int x = -radius; x <= radius; x++) {
                    if (Math.abs(x) == radius) {
                        for (int z = -radius; z <= radius; z++) {
                            checkSpots.add(centerPos.add(x, 0, z));
                        }
                    } else {
                        checkSpots.add(centerPos.add(x, 0, -radius));
                        checkSpots.add(centerPos.add(x, 0, radius));
                    }
                }

                // Check list of positions for open spot
                for (BlockPos pos : checkSpots) {
                    SpotType posType = testSpot(pos);

                    if (posType == SpotType.FREE) {
                        return pos;
                    } else if (posType == SpotType.SOURCE) {
                        foundSource = true;
                    }
                }

                radius++;
            } while (radius < SEARCH_DISTANCE_LIMIT && foundSource);

            // If we can't find a free spot
            return null;
        }
    }

}
