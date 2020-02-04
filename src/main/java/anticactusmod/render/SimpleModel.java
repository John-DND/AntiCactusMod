package anticactusmod.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;

public class SimpleModel {
	public final SimpleQuad[] quads;
	public final ResourceLocation texture;
	
	public SimpleModel(SimpleQuad[] quads, ResourceLocation texture) {
		this.quads = quads;
		this.texture = texture;
	}
	
	public void build(BufferBuilder buffer) {
		for(SimpleQuad quads : quads) {
			quads.build(buffer);
		}
	}
}
