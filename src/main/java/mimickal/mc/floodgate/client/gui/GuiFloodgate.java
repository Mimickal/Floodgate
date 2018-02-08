package mimickal.mc.floodgate.client.gui;

import mimickal.mc.floodgate.Reference;
import mimickal.mc.floodgate.container.ContainerFloodgate;
import mimickal.mc.floodgate.tileentity.TileEntityFloodgate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.List;

public class GuiFloodgate extends GuiContainer {

    /**
     * The background texture for the gui
     */
    public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID,
            "textures/gui/container/floodgate.png");

    private TileEntityFloodgate te;

    public GuiFloodgate(IInventory playerInv, TileEntityFloodgate te) {
        super(new ContainerFloodgate(playerInv, te));
        this.te = te;
        setGuiSize(176, 166);
    }

    @Override
    public void initGui() {
        super.initGui();
        //The parameters of GuiButton are(id, x, y, width, height, text);
        this.buttonList.add(new GuiButton( 1, this.guiLeft+150, this.guiTop+8, 18, 18, ""){
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY) {
                mc.getTextureManager().bindTexture(TEXTURE);
                this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 150, 8, 18, 18);
                this.mouseDragged(mc, mouseX, mouseY);
            }
        });

    }

    /**
     * Where we add our gui elements
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    /**
     * Draws the text that is an overlay
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Title of GUI
        this.drawCenteredString(mc.fontRendererObj, TextFormatting.GRAY + I18n.format("container.floodgate"),
                this.xSize/2, 4, 14737632);

        // Tool tips
        int actualMouseX = mouseX - ((this.width - this.xSize) / 2);
        int actualMouseY = mouseY - ((this.height - this.ySize) / 2);

        if (isPointInRegion(150, 8, 18, 18, mouseX, mouseY)) {
            List<String> text = new ArrayList<String>();
            text.add(TextFormatting.GRAY + I18n.format("gui.floodgate.redstone.tooltip"));
            this.drawHoveringText(text, actualMouseX, actualMouseY);
        }

        List<String> text = new ArrayList<String>();
        text.add(TextFormatting.GRAY + te.getFgstate().name());
        this.drawHoveringText(text, 150 - 10,  27 + 15);
    }


    @Override
    protected void actionPerformed(GuiButton b)
    {
        switch (b.id) {
            case 1:
                this.te.toggleFgstate();
                break;
        }

    }

}
