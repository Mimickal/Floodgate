package mimickal.mc.floodgate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFloodgate extends Container {

    private TileEntityFloodgate te;
    private IItemHandler handler;

    public ContainerFloodgate(IInventory playerInv, TileEntityFloodgate te) {
        System.out.println("\u001B[31m"+"new floodgate block"+"\u001B[0m");
        this.te = te;
        this.handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        //Our tile entity slots
        this.addSlotToContainer(new SlotItemHandler(handler, 0, 62, 17));
        this.addSlotToContainer(new SlotItemHandler(handler, 1, 80, 17));
        this.addSlotToContainer(new SlotItemHandler(handler, 2, 98, 17));
        this.addSlotToContainer(new SlotItemHandler(handler, 3, 62, 35));
        this.addSlotToContainer(new SlotItemHandler(handler, 4, 80, 35));
        this.addSlotToContainer(new SlotItemHandler(handler, 5, 98, 35));
        this.addSlotToContainer(new SlotItemHandler(handler, 6, 62, 53));
        this.addSlotToContainer(new SlotItemHandler(handler, 7, 80, 53));
        this.addSlotToContainer(new SlotItemHandler(handler, 8, 98, 53));
        this.addSlotToContainer(new SlotItemHandler(handler, 9, 134, 17));

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

}
