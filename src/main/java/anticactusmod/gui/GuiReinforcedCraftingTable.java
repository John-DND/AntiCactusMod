package anticactusmod.gui;

import anticactusmod.block.ModBlocks;
import anticactusmod.init.AntiCactusMod;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiReinforcedCraftingTable extends GuiContainer {
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(AntiCactusMod.MODID, "textures/gui/reinforced_crafting_table.png");

	private InventoryPlayer playerInv;

	public GuiReinforcedCraftingTable(Container container, InventoryPlayer playerInv) {
		super(container);
		this.playerInv = playerInv;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format("tile." + ModBlocks.TOXICITY_EXTRACTOR.name + ".name");
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
	}
}
