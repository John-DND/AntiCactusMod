package anticactusmod.block;

import anticactusmod.config.ModConfig;
import anticactusmod.init.AntiCactusMod;
import net.minecraft.block.material.Material;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = AntiCactusMod.MODID)
public class BlockBreakEventHandler {
	private static final String[] buzzwords = {"SLOW", "ZZZ", "BAD", "NOOB", "LOW IQ", "IDIOT", "ZZSUPERZZ", "HANDICAPPED", "DUMBSHIT"};
	
    @SubscribeEvent
    public static void blockBreak(BlockEvent.BreakEvent event) {
    	if(!event.getWorld().isRemote && ModConfig.buzzwordChance > 0) { //run this code client-side only
    		if(event.getWorld().getBlockState(event.getPos()).getMaterial() == Material.CACTUS) { //check material
    			if(AntiCactusMod.rng.nextInt(ModConfig.buzzwordChance) == 0) { //random chance
        			event.getPlayer().sendMessage(
        					new TextComponentTranslation(
        							buzzwords[AntiCactusMod.rng.nextInt(buzzwords.length)], 
        							new Object[0]).setStyle(new Style().setColor(TextFormatting.DARK_GREEN)));	
    			}
    		}
    	}
    }
}
