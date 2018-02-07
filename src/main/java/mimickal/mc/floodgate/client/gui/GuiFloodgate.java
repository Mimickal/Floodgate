package mimickal.mc.floodgate.client.gui;

import mimickal.mc.floodgate.Floodgate;
import mimickal.mc.floodgate.Reference;
import mimickal.mc.floodgate.container.ContainerFloodgate;
import mimickal.mc.floodgate.tileentity.TileEntityFloodgate;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.List;

public class GuiFloodgate extends GuiContainer {

    private TileEntityFloodgate te;
    private IInventory playerInv;

    public GuiFloodgate(IInventory playerInv, TileEntityFloodgate te) {
        super(new ContainerFloodgate(playerInv, te));
        this.te = te;
        this.playerInv = playerInv;
        setGuiSize(176, 166);
    }

    /**
     * Where we add our gui elements
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(
                new ResourceLocation(Reference.MOD_ID, "textures/gui/container/floodgate.png")
        );
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    /**
     * Draws the text that is an overlay
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

//        int actualMouseX = mouseX - ((this.width - this.xSize) / 2);
//        int actualMouseY = mouseY - ((this.height - this.ySize) / 2);
//
//        if (isPointInRegion(134, 17, 18, 18, mouseX, mouseY)
//                && te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
//                .getStackInSlot(9) == null) {
//            List<String> text = new ArrayList<String>();
//            text.add(TextFormatting.GRAY + I18n.format("gui.block_breaker.enchanted_book.tooltip"));
//            this.drawHoveringText(text, actualMouseX, actualMouseY);
//        }
    }


}
