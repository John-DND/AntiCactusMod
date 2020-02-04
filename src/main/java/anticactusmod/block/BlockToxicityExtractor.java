package anticactusmod.block;

import anticactusmod.item.ModItems;
import anticactusmod.render.RenderUtils;
import anticactusmod.tileentity.BlockTileEntity;
import anticactusmod.tileentity.TileEntityToxicityExtractor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockToxicityExtractor extends BlockTileEntity<TileEntityToxicityExtractor> {
	public static final AxisAlignedBB TOXICITY_EXTRACTOR_AABB = new AxisAlignedBB(0, 0, 0, RenderUtils.PX_16, RenderUtils.PX_12, RenderUtils.PX_16);
	
	public BlockToxicityExtractor() {
		super(Material.IRON, "toxicity_extractor");
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
		return TOXICITY_EXTRACTOR_AABB;
		
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		if(layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.SOLID) return true;
		else return false;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(!worldIn.isRemote) {
			if(worldIn.isAirBlock(pos.down())) {
				dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}	
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
	}
	
	public boolean canBlockStay(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos.down());
        return state.isSideSolid(worldIn, pos, EnumFacing.UP);
	}

	@Override
	public Class<TileEntityToxicityExtractor> getTileEntityClass() {
		return TileEntityToxicityExtractor.class;
	}

	@Override
	public TileEntityToxicityExtractor createTileEntity(World world, IBlockState state) {
		return new TileEntityToxicityExtractor();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) { 
		if(!world.isRemote) {
			TileEntityToxicityExtractor tile = getTileEntity(world, pos);
			ItemStack heldItem = player.getHeldItemMainhand();
			
			if(!heldItem.isEmpty()) { 
				if(!tile.isFull) { 
					tile.setItemStack(heldItem);
				}
				else if(tile.isFull && heldItem.getItem().equals(ModItems.REINFORCED_SYRINGE_EMPTY)) {
					if(heldItem.getCount() == 1) {
						player.setHeldItem(player.getActiveHand(), new ItemStack(ModItems.REINFORCED_SYRINGE_FULL));	
					}
					else {
						ItemStack fullSyringe = new ItemStack(ModItems.REINFORCED_SYRINGE_FULL);
						heldItem.setCount(heldItem.getCount() - 1);
						if(!player.inventory.addItemStackToInventory(fullSyringe)) {
							world.spawnEntity(new EntityItem(world, player.posX, player.posY, player.posZ, fullSyringe));
						}
					}
					tile.emptyTank();
				}
			}
		}
		return true;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if(!world.isRemote) {
			TileEntityToxicityExtractor tile = getTileEntity(world, pos);
			if(tile.isProcessing) {
				world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), tile.getItemStack()));
			}
			else if(tile.isFull) {
				//breaking a full tank has consequences...
			}
		}
		
		super.breakBlock(world, pos, state);
	}
}
