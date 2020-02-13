package anticactusmod.render;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.EnumFacing;

public class SimpleQuad {
	public final EnumFacing facing;
	
	public final Vector3f vertex1;
	public final Vector3f vertex2;
	public final Vector3f vertex3;
	public final Vector3f vertex4;
	
	public final Vector2f texVertex1;
	public final Vector2f texVertex2;
	public final Vector2f texVertex3;
	public final Vector2f texVertex4;

	/**
	 * Constructs a quad from the specified arguments using default winding order (the 
	 * vertices are created starting with the upper-left vertex and going around counterclockwise). 
	 * As w and h increase, the quad increases in size down and right.
	 * 
	 * What is "up" and what is "left" is determined by the direction we're facing. For up and down facing
	 * quads, "up" is south (positive Z) and "left" is east (positive X).
	 * 
	 * To visualize how this works for the other axes, imagine positioning your character such that the entire quad
	 * is visible. "up" and "left" now correspond to up and left on your screen. 
	 * 
	 * @param facing The direction the quad faces
	 * @param x The x-coordinate of the origin of the quad
	 * @param y The y-coordinate of the origin of the quad
	 * @param z The z-coordinate of the origin of the quad
	 * @param w The width of the quad
	 * @param h The height of the quad
	 * @param u The x-coordinate of the UV origin
	 * @param v The y-coordinate of the UV origin
	 * @param uvW The width of the UV 
	 * @param uvH The height of the UV 
	 */
	public SimpleQuad(EnumFacing facing, float x, float y, float z, float w, float h, float u, float v, float uvW, float uvH) {
		this.facing = facing;
		
		texVertex1 = new Vector2f(u, v);
		texVertex2 = new Vector2f(u, v - uvH);
		texVertex3 = new Vector2f(u + uvW, v - uvH);
		texVertex4 = new Vector2f(u + uvW, v);
		
		switch(facing) {
		case UP:
			vertex1 = new Vector3f(x, y, z);
			vertex2 = new Vector3f(x, y, z + w);
			vertex3 = new Vector3f(x + w, y, z + w);
			vertex4 = new Vector3f(x + w, y, z);
			break;
		case DOWN:
			vertex1 = new Vector3f(x, y, z);
			vertex2 = new Vector3f(x, y, z + w);
			vertex3 = new Vector3f(x - w, y, z + w);
			vertex4 = new Vector3f(x - w, y, z);
			break;
		case NORTH:
			vertex1 = new Vector3f(x, y, z);
			vertex2 = new Vector3f(x, y - h, z);
			vertex3 = new Vector3f(x - w, y - h, z);
			vertex4 = new Vector3f(x - w, y, z);
			break;
		case SOUTH:
			vertex1 = new Vector3f(x, y, z);
			vertex2 = new Vector3f(x, y - h, z);
			vertex3 = new Vector3f(x + w, y - h, z);
			vertex4 = new Vector3f(x + w, y, z);
			break;
		case EAST:
			vertex1 = new Vector3f(x, y, z);
			vertex2 = new Vector3f(x, y - h, z);
			vertex3 = new Vector3f(x, y - h, z - w);
			vertex4 = new Vector3f(x, y, z - w);
			break;
		case WEST:
			vertex1 = new Vector3f(x, y, z);
			vertex2 = new Vector3f(x, y - h, z);
			vertex3 = new Vector3f(x, y - h, z + w);
			vertex4 = new Vector3f(x, y, z + w);
			break;
		default:
			vertex1 = new Vector3f();
			vertex2 = new Vector3f();
			vertex3 = new Vector3f();
			vertex4 = new Vector3f();
			break;
		}
	}
	
	public void build(BufferBuilder buffer) {
		buffer.pos(vertex1.x, vertex1.y, vertex1.z).tex(texVertex1.x, texVertex1.y).endVertex();
		buffer.pos(vertex2.x, vertex2.y, vertex2.z).tex(texVertex2.x, texVertex2.y).endVertex();
		buffer.pos(vertex3.x, vertex3.y, vertex3.z).tex(texVertex3.x, texVertex3.y).endVertex();
		buffer.pos(vertex4.x, vertex4.y, vertex4.z).tex(texVertex4.x, texVertex4.y).endVertex();
	}
}
