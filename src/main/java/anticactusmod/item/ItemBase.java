package anticactusmod.item;

import anticactusmod.init.AntiCactusMod;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemBase extends Item {
	public ItemBase(String name) {
		setTranslationKey(name);
		setRegistryName(name);
	}
	
	public void registerItemModel() {
		AntiCactusMod.proxy.registerItemRenderer(this, 0);
	}
	
	public void register(IForgeRegistry<Item> registry) {
		registry.register(this);
	}
}
