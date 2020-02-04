package anticactusmod.item;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {
	public static final ItemBase TOXICITY_CRYSTAL = new ItemBase("toxicity_crystal");
	public static final ItemBase REINFORCED_SYRINGE_EMPTY = new ItemBase("reinforced_syringe_empty");
	public static final ItemBase REINFORCED_SYRINGE_FULL = new ItemBase("reinforced_syringe_full");
	
	public static void register(IForgeRegistry<Item> registry) {
		TOXICITY_CRYSTAL.register(registry);
		REINFORCED_SYRINGE_EMPTY.register(registry);
		REINFORCED_SYRINGE_FULL.register(registry);
	}
	
	public static void registerModels() {
		TOXICITY_CRYSTAL.registerItemModel();
		REINFORCED_SYRINGE_EMPTY.registerItemModel();
		REINFORCED_SYRINGE_FULL.registerItemModel();
	}
}
