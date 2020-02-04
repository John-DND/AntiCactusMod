package anticactusmod.tileentity;

import java.util.Hashtable;

import anticactusmod.init.AntiCactusMod;
import anticactusmod.net.PacketRequestUpdateToxicityExtractor;
import anticactusmod.net.PacketUpdateToxicityExtractor;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityToxicityExtractor extends TileEntity implements ITickable {
	public static Hashtable<Item, Integer> recipes = new Hashtable<Item, Integer>();
	
	public long startTime;
	public int currentTicks;
	public int ticksRequired;
	
	public boolean isProcessing = false;
	public boolean isFull = false;
	
	static {
		recipes.put(Item.getItemFromBlock(Blocks.CACTUS), 200);
	}

	private ItemStackHandler inventory = new ItemStackHandler(1);
	
	@Override
	public void onLoad() {
		if (world.isRemote) {
			AntiCactusMod.network.sendToServer(new PacketRequestUpdateToxicityExtractor(this));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setLong("startTime", startTime);
		compound.setInteger("currentTicks", currentTicks);
		compound.setInteger("ticksRequired", ticksRequired);
		compound.setBoolean("isProcessing", isProcessing);
		compound.setBoolean("isFull", isFull);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		startTime = compound.getLong("startTime");
		currentTicks = compound.getInteger("currentTicks");
		ticksRequired = compound.getInteger("ticksRequired");
		isProcessing = compound.getBoolean("isProcessing");
		isFull = compound.getBoolean("isFull");
		super.readFromNBT(compound);
	}
	
	public ItemStack getItemStack() {
		return inventory.getStackInSlot(0).copy();
	}
	
	public void setItemStack(ItemStack item) {
		if(inventory.getStackInSlot(0).isEmpty() && !isFull && !isProcessing && TileEntityToxicityExtractor.recipes.getOrDefault(item.getItem(), -1) != -1) {
			inventory.insertItem(0, item.splitStack(1), false);
			
			startTime = world.getTotalWorldTime();
			ticksRequired = recipes.get(inventory.getStackInSlot(0).getItem());
			
			isProcessing = true;
			isFull = false;
			
			if(!world.isRemote) {
				AntiCactusMod.network.sendToAllAround(new PacketUpdateToxicityExtractor(TileEntityToxicityExtractor.this), 
						new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));		
			}	
		}
	}
	
	public void emptyTank() {
		if(isFull) {
			isFull = false;
			
			if(!world.isRemote) {
				AntiCactusMod.network.sendToAllAround(new PacketUpdateToxicityExtractor(TileEntityToxicityExtractor.this), 
						new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));	
			}	
		}	
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {
			if(!isFull && isProcessing && ++currentTicks == ticksRequired) {
				startTime = 0;
				currentTicks = 0;
				ticksRequired = 0;
				
				isProcessing = false;
				isFull = true;
				
				inventory.extractItem(0, 1, false);
				
				AntiCactusMod.network.sendToAllAround(new PacketUpdateToxicityExtractor(TileEntityToxicityExtractor.this), 
						new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));	
			}
		}
	}
}