package mimickal.mc.floodgate.container;

import mimickal.mc.floodgate.tileentity.TileEntityFloodgate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFloodgate extends Container {

    private TileEntityFloodgate te;
    private IItemHandler handler;

    public ContainerFloodgate(IInventory playerInv, TileEntityFloodgate te) {
        this.te = te;
        this.handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        //Our tile entity slots
        // TODO: this slot is for input
        this.addSlotToContainer(new SlotItemHandler(handler, 0, 38, 17));
        this.addSlotToContainer(new SlotItemHandler(handler, 1, 38, 62));
        this.addSlotToContainer(new SlotItemHandler(handler, 2, 152, 44));
        this.addSlotToContainer(new SlotItemHandler(handler, 3, 152, 62));


        //The player's inventory slots
        int xPos = 8; //The x position of the top left player inventory slot on our texture
        int yPos = 84; //The y position of the top left player inventory slot on our texture

        //Player slots
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
            }
        }

        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInv, x, xPos + x * 18, yPos + 58));
        }
    }

    /**
     * Checks that the player can put items in and out of the container
     */
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.te.isUseableByPlayer(player);
    }

    /**
     * Called when the player presses shift and takes an item out of the container
     * TODO: crashes
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
        ItemStack previous = (ItemStack) null;
        Slot slot = (Slot) this.inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            if (fromSlot < this.handler.getSlots()) {
                // From the block breaker inventory to player's inventory
                if (!this.mergeItemStack(current, handler.getSlots(), handler.getSlots() + 36, true))
                    return (ItemStack) null;
            } else {
                // From the player's inventory to block breaker's inventory
                if (current.getItem() == Items.ENCHANTED_BOOK) {
                    if (!this.mergeItemStack(current, 9, handler.getSlots(), false))
                        return (ItemStack) null;
                }
                if (!this.mergeItemStack(current, 0, handler.getSlots(), false))
                    return (ItemStack) null;
            }

            if (current.stackSize == 0) //Use func_190916_E() instead of stackSize 1.11 only 1.11.2 use getCount()
                slot.putStack((ItemStack) null); //Use ItemStack.field_190927_a instead of (ItemStack)null for a blank item stack. In 1.11.2 use ItemStack.EMPTY
            else
                slot.onSlotChanged();

            if (current.stackSize == previous.stackSize)
                return (ItemStack) null;

//            slot.onTake(playerIn, current);
        }
        return previous;
    }

}
