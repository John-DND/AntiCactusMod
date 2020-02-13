package anticactusmod.render;

import anticactusmod.init.AntiCactusMod;
import net.minecraft.util.ResourceLocation;

public class ModModels {
	public static SimpleModel TOXICITY_FLUID_MODEL;
	
	public static void load() {
		TOXICITY_FLUID_MODEL = new SimpleModel(RenderUtils.generateCuboid(RenderUtils.PX_4, RenderUtils.PX_4, RenderUtils.PX_4), 
				new ResourceLocation(AntiCactusMod.MODID + ":textures/blocks/toxicity.png"));
		
		TOXICITY_FLUID_MODEL.loadTexture();
	}
}
