package anticactusmod.config;

import anticactusmod.init.AntiCactusMod;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid=AntiCactusMod.MODID)
@Mod.EventBusSubscriber(modid = AntiCactusMod.MODID)
public class ModConfig {
	@Config.Comment({"Chance (1/x) of sending a buzzword whenever a cactus is broken by the player."})
	@Config.Name("Buzzword Chance")
	@Config.RangeInt(min=0, max=69420)
	public static int buzzwordChance = 10;
	
	@SubscribeEvent
	public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(AntiCactusMod.MODID)) {
			ConfigManager.sync(AntiCactusMod.MODID, Config.Type.INSTANCE);
		}
	}
}
