package anticactusmod.block;

import anticactusmod.init.AntiCactusMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockBase extends Block {
	public final String name;
	public final Item itemBlock;

	public BlockBase(Material material, String id) {
		super(material);
		name = id;
	
		setTranslationKey(name);
		setRegistryName(name);
		
		itemBlock = new ItemBlock(this).setRegistryName(name);
	}
	
	public void registerItemModel() {
		AntiCactusMod.proxy.registerItemRenderer(itemBlock, 0);
	}
	
	public void registerItemBlock(IForgeRegistry<Item> registry) {
		registry.register(itemBlock);
	}
}
