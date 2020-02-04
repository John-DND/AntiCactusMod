package anticactusmod.net;

import anticactusmod.tileentity.TileEntityToxicityExtractor;
import io.netty.buffer.ByteBuf;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateToxicityExtractor implements IMessage {
	public BlockPos pos;
	public ItemStack stack;
	
	public long startTime;
	public int ticksRequired;
	
	public boolean isProcessing;
	public boolean isFull;
	
	public PacketUpdateToxicityExtractor(BlockPos pos, ItemStack stack, long startTime, int ticksRequired, boolean isFull, boolean isProcessing) {
		this.pos = pos;
		this.stack = stack;
		
		this.startTime = startTime;
		this.ticksRequired = ticksRequired;
		
		this.isProcessing = isProcessing;
		this.isFull = isFull;
	}
	
	public PacketUpdateToxicityExtractor(TileEntityToxicityExtractor te) {
		this(te.getPos(), te.getItemStack(), te.startTime, te.ticksRequired, te.isFull, te.isProcessing);
	}
	
	public PacketUpdateToxicityExtractor() { }

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		ByteBufUtils.writeItemStack(buf, stack);
		
		buf.writeLong(startTime);
		buf.writeInt(ticksRequired);
		
		buf.writeBoolean(isProcessing);
		buf.writeBoolean(isFull);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		stack = ByteBufUtils.readItemStack(buf);
		
		startTime = buf.readLong();
		ticksRequired = buf.readInt();
		
		isProcessing = buf.readBoolean();
		isFull = buf.readBoolean();
	}
	
	public static class Handler implements IMessageHandler<PacketUpdateToxicityExtractor, IMessage> {
		@Override
		public IMessage onMessage(PacketUpdateToxicityExtractor message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntityToxicityExtractor te = (TileEntityToxicityExtractor)Minecraft.getMinecraft().world.getTileEntity(message.pos);
				te.setItemStack(message.stack);
				
				te.startTime = message.startTime;
				te.ticksRequired = message.ticksRequired;
				
				te.isProcessing = message.isProcessing;
				te.isFull = message.isFull;
			});
			return null;
		}
	}
}
