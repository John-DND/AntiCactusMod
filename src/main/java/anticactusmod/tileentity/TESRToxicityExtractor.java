package anticactusmod.tileentity;

import org.lwjgl.opengl.GL11;

import anticactusmod.render.ModModels;
import anticactusmod.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;

public class TESRToxicityExtractor extends TileEntitySpecialRenderer<TileEntityToxicityExtractor> {
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
			
			GlStateManager.translate(x + 0.5, y + 0.6875, z + 0.5); 

			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, te.getWorld(), null);
			model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
			
			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
			
			GlStateManager.pushMatrix();
			
			ModModels.TOXICITY_FLUID_MODEL.init(x + RenderUtils.PX_6, y + RenderUtils.PX_11, z + RenderUtils.PX_6);
			GlStateManager.scale(1F, Math.min(((te.getWorld().getTotalWorldTime() - te.startTime) + partialTicks) / (float)te.ticksRequired, 1F), 1F);
			ModModels.TOXICITY_FLUID_MODEL.render();
			
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

			ModModels.TOXICITY_FLUID_MODEL.init(x + RenderUtils.PX_6, y + RenderUtils.PX_11, z + RenderUtils.PX_6);
			ModModels.TOXICITY_FLUID_MODEL.render();
			
			GlStateManager.disableRescaleNormal();
			GlStateManager.disableBlend();
		    RenderHelper.disableStandardItemLighting();
		    
			GlStateManager.popMatrix();
		}
	}
}