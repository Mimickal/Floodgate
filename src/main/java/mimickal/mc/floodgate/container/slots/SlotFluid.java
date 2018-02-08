package mimickal.mc.floodgate.container.slots;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFluid extends SlotItemHandler {

    public SlotFluid(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    @SuppressWarnings( "deprecation")
    public boolean isItemValid(ItemStack stack) {
        if (Loader.isModLoaded("IC2")) {
            Item fluid_cell = GameRegistry.findItem("IC2","fluid_cell");
            return super.isItemValid(stack) &&
                    (stack.getItem() == Items.WATER_BUCKET || stack.getItem() == fluid_cell);
        }
        return super.isItemValid(stack) && stack.getItem() == Items.WATER_BUCKET;
    }
}
