package anticactusmod.gui;

import anticactusmod.init.AntiCactusMod;
import anticactusmod.tileentity.TileEntityReinforcedCraftingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler {
	public static final int REINFORCED_CRAFTING_TABLE = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		AntiCactusMod.logger.info("getServerGuiElement called.");
		switch (ID) {
		case REINFORCED_CRAFTING_TABLE:
			return new ContainerReinforcedCraftingTable(player.inventory, (TileEntityReinforcedCraftingTable)world.getTileEntity(new BlockPos(x, y, z)));
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		AntiCactusMod.logger.info("getClientGuiElement called.");
		switch (ID) {
		case REINFORCED_CRAFTING_TABLE:
			return new GuiReinforcedCraftingTable((Container)getServerGuiElement(ID, player, world, x, y, z), player.inventory);
		default:
			return null;
		}
	}

}
