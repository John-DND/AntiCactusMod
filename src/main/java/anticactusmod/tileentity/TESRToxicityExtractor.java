package anticactusmod.tileentity;

import org.lwjgl.opengl.GL11;

import anticactusmod.init.AntiCactusMod;
import anticactusmod.render.SimpleQuad;
import anticactusmod.render.RenderUtils;
import anticactusmod.render.SimpleModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

public class TESRToxicityExtractor extends TileEntitySpecialRenderer<TileEntityToxicityExtractor> {
	private static final SimpleModel TOXICITY_FLUID_MODEL= new SimpleModel(new SimpleQuad[]
	{
			new SimpleQuad(EnumFacing.UP, RenderUtils.PX_6, RenderUtils.PX_11, RenderUtils.PX_6, RenderUtils.PX_4, RenderUtils.PX_4, 0, 0, 1, 1),
			new SimpleQuad(EnumFacing.DOWN, RenderUtils.PX_10, RenderUtils.PX_7, RenderUtils.PX_6, RenderUtils.PX_4, RenderUtils.PX_4, 0, 0, RenderUtils.PX_1, RenderUtils.PX_1),
					
			new SimpleQuad(EnumFacing.NORTH, RenderUtils.PX_10, RenderUtils.PX_11, RenderUtils.PX_6, RenderUtils.PX_4, RenderUtils.PX_4, 0, 0, RenderUtils.PX_1, RenderUtils.PX_1),
			new SimpleQuad(EnumFacing.SOUTH, RenderUtils.PX_6, RenderUtils.PX_11, RenderUtils.PX_10, RenderUtils.PX_4, RenderUtils.PX_4, 0, 0, RenderUtils.PX_1, RenderUtils.PX_1),
					
			new SimpleQuad(EnumFacing.EAST, RenderUtils.PX_10, RenderUtils.PX_11, RenderUtils.PX_10, RenderUtils.PX_4, RenderUtils.PX_4, 0, 0, RenderUtils.PX_1, RenderUtils.PX_1),
			new SimpleQuad(EnumFacing.WEST, RenderUtils.PX_6, RenderUtils.PX_11, RenderUtils.PX_6, RenderUtils.PX_4, RenderUtils.PX_4, 0, 0, RenderUtils.PX_1, RenderUtils.PX_1)
	}, new ResourceLocation(AntiCactusMod.MODID + ":textures/blocks/toxicity.png"));
	
	@Override
	public void render(TileEntityToxicityExtractor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if(te.isProcessing) {
			ItemStack stack = te.getItemStack();
			
			//render the item
			GlStateManager.pushMatrix();
			
			GlStateManager.enableRescaleNormal();
			GlStateManager.enableBlend();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			
			GlStateManager.translate(x + 0.5, y + 0.6875, z + 0.5); //this places the cactus directly in the extractor where he and his 'friends' belong

			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
			model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
			
			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
			
			GlStateManager.pushMatrix();
			
			bindTexture(TOXICITY_FLUID_MODEL.texture);
			
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();
			
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

			GlStateManager.translate(x + RenderUtils.PX_10, y + RenderUtils.PX_7, z + RenderUtils.PX_6);
			GlStateManager.scale(1, Math.min(((te.getWorld().getTotalWorldTime() - te.startTime) + partialTicks) / (double)te.ticksRequired, 1), 1);
			GlStateManager.translate(-RenderUtils.PX_10, -RenderUtils.PX_7, -RenderUtils.PX_6);
			
			TOXICITY_FLUID_MODEL.build(buffer);
			
			tessellator.draw();
			buffer.reset();
			
			GlStateManager.disableBlend();
		    RenderHelper.disableStandardItemLighting();
			GlStateManager.popMatrix();
		}
		else if(te.isFull) {
			GlStateManager.pushMatrix();
			GlStateManager.enableRescaleNormal();
			GlStateManager.enableBlend();
			RenderHelper.enableStandardItemLighting();
			
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			
			bindTexture(TOXICITY_FLUID_MODEL.texture);
			
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();
			
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			
			GlStateManager.translate(x, y, z);
			
			TOXICITY_FLUID_MODEL.build(buffer);
			
			tessellator.draw();
			buffer.reset();
			
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableBlend();
		    RenderHelper.disableStandardItemLighting();
			GlStateManager.popMatrix();
		}
	}
}