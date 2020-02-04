package anticactusmod.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import anticactusmod.block.ModBlocks;
import anticactusmod.item.ModItems;
import anticactusmod.net.PacketRequestUpdateToxicityExtractor;
import anticactusmod.net.PacketUpdateToxicityExtractor;
import anticactusmod.proxy.CommonProxy;

@Mod(modid = AntiCactusMod.MODID, name = AntiCactusMod.MODNAME, version = AntiCactusMod.VERSION, acceptedMinecraftVersions=AntiCactusMod.ACCEPTED_MINECRAFT_VERSIONS)
public class AntiCactusMod
{
    public static final String MODID = "anticactusmod";
    public static final String MODNAME = "Anti Cactus Mod";
    public static final String VERSION = "0.0.1";
    public static final String ACCEPTED_MINECRAFT_VERSIONS = "[1.12]";
    
	@Mod.Instance(MODID)
	public static AntiCactusMod instance;
	
	public static Random rng = new Random();
    public static Logger logger;
    
    @SidedProxy(serverSide = "anticactusmod.proxy.CommonProxy", clientSide = "anticactusmod.proxy.ClientProxy")
    public static CommonProxy proxy;
    
    public static SimpleNetworkWrapper network;
    
	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			ModItems.register(event.getRegistry());
			ModBlocks.registerItemBlocks(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			ModBlocks.register(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerModels(ModelRegistryEvent event) {
			ModItems.registerModels();
			ModBlocks.registerModels();
		}
	}
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = LogManager.getLogger(MODID);
    	
		network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		network.registerMessage(new PacketUpdateToxicityExtractor.Handler(), PacketUpdateToxicityExtractor.class, 0, Side.CLIENT);
		network.registerMessage(new PacketRequestUpdateToxicityExtractor.Handler(), PacketRequestUpdateToxicityExtractor.class, 1, Side.SERVER);
		
		proxy.registerRenderers();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
       
    }
    
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}


