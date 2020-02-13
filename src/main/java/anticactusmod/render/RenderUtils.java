package anticactusmod.render;

import org.lwjgl.util.vector.Vector2f;

import net.minecraft.util.EnumFacing;

public class RenderUtils {
	public static final float PX_1 = 0.0625F;
	public static final float PX_2 = 0.125F;
	public static final float PX_3 = 0.1875F;
	public static final float PX_4 = 0.25F;
	
	public static final float PX_5 = 0.3125F;
	public static final float PX_6 = 0.375F;
	public static final float PX_7 = 0.4375F;
	public static final float PX_8 = 0.5F;
	
	public static final float PX_9 = 0.5625F;
	public static final float PX_10 = 0.625F;
	public static final float PX_11 = 0.6875F;
	public static final float PX_12 = 0.75F;
	
	public static final float PX_13 = 0.8125F;
	public static final float PX_14 = 0.875F;
	public static final float PX_15 = 0.9375F;
	public static final float PX_16 = 1F;
	
	public static final Vector2f[] DEFAULT_MAPPINGS = {
			new Vector2f(0, 0),
			new Vector2f(1, 1),
			
			new Vector2f(0, 0),
			new Vector2f(1, 1),
			
			new Vector2f(0, 0),
			new Vector2f(1, 1),
			
			new Vector2f(0, 0),
			new Vector2f(1, 1),
			
			new Vector2f(0, 0),
			new Vector2f(1, 1),
			
			new Vector2f(0, 0),
			new Vector2f(1, 1)
	};
	
	/**
	 * Generates six SimpleQuads that can be rendered as a cuboid. The exact order is as follows:
	 * 
	 * Index 0: up facing quad
	 * Index 1: south facing quad
	 * Index 2: east facing quad
	 * Index 3: north facing quad
	 * Index 4: west facing quad
	 * Index 5: down facing quad
	 * 
	 * As width, height, and depth increase, the resulting model will grow east, down, south, respectively.
	 * 
	 * Texture coordinates can be supplied to every quad by passing an array to texcoords. It should be formatted 
	 * such that there are 12 total vectors, a pair of vectors for each quad. The first of every pair should be the 
	 * UV origin, and the second pair should be the width and height of the resulting UV region.
	 */
	public static SimpleQuad[] generateCuboid(float x, float y, float z, float width, float height, float depth, Vector2f[] texcoords) {
		SimpleQuad[] model = new SimpleQuad[6];
		model[0] = new SimpleQuad(EnumFacing.UP, x, y, z, width, depth, texcoords[0].x, texcoords[0].y, texcoords[1].x, texcoords[1].y);
		model[1] = new SimpleQuad(EnumFacing.SOUTH, x, y, z + depth, width, height, texcoords[2].x, texcoords[2].y, texcoords[3].x, texcoords[3].y);
		model[2] = new SimpleQuad(EnumFacing.EAST, x + width, y, z + depth, depth, height, texcoords[4].x, texcoords[4].y, texcoords[5].x, texcoords[5].y);
		model[3] = new SimpleQuad(EnumFacing.NORTH, x + width, y, z, width, height, texcoords[6].x, texcoords[6].y, texcoords[7].x, texcoords[7].y);
		model[4] = new SimpleQuad(EnumFacing.WEST, x, y, z, depth, height, texcoords[8].x, texcoords[8].y, texcoords[9].x, texcoords[9].y);
		model[5] = new SimpleQuad(EnumFacing.DOWN, x + width, y - height, z, width, depth, texcoords[10].x, texcoords[10].y, texcoords[11].x, texcoords[11].y);
		return model;
	}
	
	public static SimpleQuad[] generateCuboid(float x, float y, float z, float width, float height, float depth) {
		return generateCuboid(x, y, z, width, height, depth, DEFAULT_MAPPINGS);
	}
	
	public static SimpleQuad[] generateCuboid(float width, float height, float depth) {
		return generateCuboid(0, 0, 0, width, height, depth, DEFAULT_MAPPINGS);
	}
	
	public static SimpleQuad[] generateCube(float x, float y, float z, float width, Vector2f[] texcoords) {
		return generateCuboid(x, y, z, width, width, width, texcoords);
	}
	
	public static SimpleQuad[] generateCube(float x, float y, float z, float width) {
		return generateCuboid(x, y, z, width, width, width, DEFAULT_MAPPINGS);
	}
	
	public static SimpleQuad[] generateCube(float width) {
		return generateCuboid(0, 0, 0, width, width, width, DEFAULT_MAPPINGS);
	}
}
