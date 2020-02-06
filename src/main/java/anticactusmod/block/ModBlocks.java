package anticactusmod.block;

import anticactusmod.tileentity.TileEntityReinforcedCraftingTable;
import anticactusmod.tileentity.TileEntityToxicityExtractor;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {
	public static final BlockBase TOXICITY_EXTRACTOR = new BlockToxicityExtractor();
	public static final BlockBase REINFORCED_CRAFTING_TABLE = new BlockReinforcedCraftingTable();
	
	public static void register(IForgeRegistry<Block> registry) {
		registry.register(TOXICITY_EXTRACTOR);
		registry.register(REINFORCED_CRAFTING_TABLE);
		
		TileEntityToxicityExtractor.register("toxicity_extractor_tile_entity", TileEntityToxicityExtractor.class);
		TileEntityReinforcedCraftingTable.register("reinforced_crafting_table_tile_entity", TileEntityReinforcedCraftingTable.class);
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry) {
		TOXICITY_EXTRACTOR.registerItemBlock(registry);
		REINFORCED_CRAFTING_TABLE.registerItemBlock(registry);
	}
	
	public static void registerModels() {
		TOXICITY_EXTRACTOR.registerItemModel();
		REINFORCED_CRAFTING_TABLE.registerItemModel();
	}
}