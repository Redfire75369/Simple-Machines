package redfire.mods.simplemachinery.tileentities.generic;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import redfire.mods.simplemachinery.SimpleMachinery;

public class GuiMachine<TE extends TileMachine, Container extends ContainerMachine> extends GuiContainer {
    public static final int width = 176;
    public static final int height = 166;

    protected static ResourceLocation background;

    protected final TE tileEntity;

    public GuiMachine(TE tileEntity, Container container, String resourcePath) {
        super(container);
        xSize = width;
        ySize = height;

        background = new ResourceLocation(SimpleMachinery.modid, resourcePath);

        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        super.renderHoveredToolTip(mouseX, mouseY);
    }
}