package anticactusmod.tileentity;

import javax.annotation.Nullable;

import anticactusmod.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockTileEntity<T extends TileEntity> extends BlockBase {

	public BlockTileEntity(Material material, String id) {
		super(material, id);
	}
	
	public abstract Class<T> getTileEntityClass();
	
	@SuppressWarnings("unchecked")
	public T getTileEntity(IBlockAccess world, BlockPos pos) {
		return (T)world.getTileEntity(pos);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public abstract T createTileEntity(World world, IBlockState state);
}
