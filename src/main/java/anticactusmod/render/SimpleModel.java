package anticactusmod.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class SimpleModel {
	public final SimpleQuad[] quads;
	public final ResourceLocation texture;
	
	public static final Tessellator tessellator = Tessellator.getInstance();
	public static final BufferBuilder buffer = tessellator.getBuffer();
	
	/**
	 * the transform origin defaults to the first vertex in the first quad
	 */
	private Vector3f transformOrigin;
	
	public SimpleModel(SimpleQuad[] quads, ResourceLocation texture) {
		this.quads = quads;
		this.texture = texture;
		
		transformOrigin = new Vector3f(quads[0].vertex1.x, quads[0].vertex1.y, quads[0].vertex1.z);
	}
	
	public void setTransformOrigin(float x, float y, float z) {
		transformOrigin.x = x;
		transformOrigin.y = y;
		transformOrigin.z = z;
	}
	
	public void scale(float x, float y, float z) {
		GlStateManager.translate(transformOrigin.x, transformOrigin.y, transformOrigin.z);
		GlStateManager.scale(x, y, z);
		GlStateManager.translate(-transformOrigin.x, -transformOrigin.y, -transformOrigin.z);
	}
	
	public void rotate(float angle, float x, float y, float z) {
		GlStateManager.translate(transformOrigin.x, transformOrigin.y, transformOrigin.z);
		GlStateManager.rotate(angle, x, y, z);
		GlStateManager.translate(-transformOrigin.x, -transformOrigin.y, -transformOrigin.z);
	}
	
	public void translate(float x, float y, float z) {
		GlStateManager.translate(x, y, z);
	}
	
	public void init() {
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
	}

	public void build() {
		for(SimpleQuad quads : quads) {
			quads.build(buffer);
		}
	}
	
	public void render() {
		build();
		
		tessellator.draw();
		buffer.reset();
	}
}
