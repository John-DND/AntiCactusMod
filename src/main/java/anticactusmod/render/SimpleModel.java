package anticactusmod.render;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import anticactusmod.init.AntiCactusMod;
import io.netty.handler.logging.LogLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class SimpleModel {
	public final SimpleQuad[] quads;
	public final ResourceLocation texture;
	
	public static final Tessellator tessellator = Tessellator.getInstance();
	public static final BufferBuilder buffer = tessellator.getBuffer();
	
    Vector3f origin;
    
    /**
     * Works the same as {@link #SimpleModel(SimpleQuad[], ResourceLocation)}, but also 
     * sets the transform origin.
     * 
     * This will not effect any ensuing GL calls unless {@link #init()} or {@link #init(double, double, double)} is
     * called sometime before the transforms are applied. 
     * 
     * Obviously do not call this on the server side!
     * @param quads The model to render.
     * @param texture The texture to map to the model.
     * @param origin The transform origin of the model.
     */
	public SimpleModel(final SimpleQuad[] quads, ResourceLocation texture, Vector3f origin) {
		this.quads = quads;
		this.texture = texture;
		this.origin = new Vector3f(origin.x, origin.y, origin.z);
	}
	
	/**
	 * Sets up the resources required to render the model, but does not call any GL functions in order to 
	 * prevent weird behavior when it's not constructed in some sort of rendering context.
	 * @param quads The model to render.
	 * @param texture The texture to map to the model.
	 */
	public SimpleModel(final SimpleQuad[] quads, ResourceLocation texture) {
		this(quads, texture, new Vector3f(quads[0].vertex1.x, quads[0].vertex1.y, quads[0].vertex1.z));
	}
	
	
	/**
	 * Translates the model's origin by the specified amount. Calls {@link GlStateManager#translate(float, float, float)} 
	 * to ensure that any ensuing GL transforms work as expected. This will not change the final coordinates of the model, as
	 * the change is negated before actually rendering.
	 * @param x
	 * @param y
	 * @param z
	 */
	public void translateOrigin(float x, float y, float z) {
		origin.x += x;
		origin.y += y;
		origin.z += z;
		
		GlStateManager.translate(x, y, z);
	}
	
	/**
	 * Gets a copy of the Vector3f used internally to keep track of the transform origin.
	 * @return A copy of the model's origin.
	 */
	public Vector3f getOrigin() {
		return new Vector3f(origin.x, origin.y, origin.z);
	}
	
	/**
	 * Sets the initial coordinate space to the origin of the model and binds to the stored texture. 
	 * This is useful for models that should be rendered to the screen coordinate space instead of 
	 * the world coordinate space. 
	 * 
	 * After calling this method, transforms can be applied in standard OpenGL fashion, and they all
	 * occur relative to the model's transform origin.
	 */
	public void init() {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		GlStateManager.translate(origin.x, origin.y, origin.z);
	}
	
	/**
	 * Sets the initial coordinate space to specified coordinates and binds to the stored texture. This 
	 * is useful for models that should be rendered to world coordinate space. 
	 * 
	 * After calling this method, transforms can be applied in standard OpenGL fashion, and they all
	 * occur relative to the model's transform origin.
	 */
	public void init(double x, double y, double z) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		GlStateManager.translate(x, y, z);
	}
	
	/**
	 * Unloads the texture associated with the model. Only use this if you really won't be using 
	 * it for a long time, as a subsequent call to render() will load the texture (and probably cause some
	 * annoying lag).
	 */
	public void deleteTexture() {
		Minecraft.getMinecraft().renderEngine.deleteTexture(texture);
	}
	
	/**
	 * Loads the texture associated with the model. It's not absolutely necessary to call this, as it
	 * will be loaded directly before rendering if it hasn't been already, but this may cause lag.
	 */
	public void loadTexture() {
		SimpleTexture tex = new SimpleTexture(texture);
		
		try {
			tex.loadTexture(Minecraft.getMinecraft().getResourceManager());
			Minecraft.getMinecraft().renderEngine.loadTexture(texture, tex);
		} catch (IOException e) {
			AntiCactusMod.logger.log(Level.ERROR, "Texture failed to load: " + e.getMessage());
		}
	}
	
	/**
	 * Builds and renders the model. Expected vertex format is POSITION_TEX. Calls {@link SimpleQuad#build(BufferBuilder)}
	 * on every quad in the model. 
	 */
	public void render() {
		GlStateManager.translate(-origin.x, -origin.y, -origin.z);
		
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		
		for(SimpleQuad quads : quads) {
			quads.build(buffer);
		}
		
		tessellator.draw();
		buffer.reset();
	}
}
