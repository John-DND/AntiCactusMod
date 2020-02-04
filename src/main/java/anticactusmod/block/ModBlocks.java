package anticactusmod.block;

import anticactusmod.tileentity.TileEntityToxicityExtractor;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {
	public static final BlockBase TOXICITY_EXTRACTOR = new BlockToxicityExtractor();
	
	public static void register(IForgeRegistry<Block> registry) {
		registry.register(TOXICITY_EXTRACTOR);
		
		TileEntityToxicityExtractor.register("toxicity_extractor_tile_entity", TileEntityToxicityExtractor.class);
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry) {
		TOXICITY_EXTRACTOR.registerItemBlock(registry);
	}
	
	public static void registerModels() {
		TOXICITY_EXTRACTOR.registerItemModel();
	}
}