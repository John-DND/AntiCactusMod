package anticactusmod.block;

import anticactusmod.gui.ModGuiHandler;
import anticactusmod.init.AntiCactusMod;
import anticactusmod.tileentity.BlockTileEntity;
import anticactusmod.tileentity.TileEntityReinforcedCraftingTable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockReinforcedCraftingTable extends BlockTileEntity<TileEntityReinforcedCraftingTable> {

	public BlockReinforcedCraftingTable() {
		super(Material.IRON, "reinforced_crafting_table");
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			AntiCactusMod.logger.info("Block activated.");
 			if (!player.isSneaking()) {
 			
  			} else {
  				AntiCactusMod.logger.info("Player is sneaking... GUI should be opening.");
  				player.openGui(AntiCactusMod.instance, ModGuiHandler.REINFORCED_CRAFTING_TABLE, world, pos.getX(), pos.getY(), pos.getZ());
  			}
  		}
  		return true;

	}
	
	@Override
	public Class<TileEntityReinforcedCraftingTable> getTileEntityClass() {
		return TileEntityReinforcedCraftingTable.class;
	}

	@Override
	public TileEntityReinforcedCraftingTable createTileEntity(World world, IBlockState state) {
		return new TileEntityReinforcedCraftingTable();
	}

}
