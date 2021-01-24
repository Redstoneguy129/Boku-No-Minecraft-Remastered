package me.redstoneguy129.gillygogs.bokunominecraft.common.networking;

import io.netty.buffer.ByteBuf;
import me.redstoneguy129.gillygogs.bokunominecraft.client.BNMRKeyBinds;
import me.redstoneguy129.gillygogs.bokunominecraft.common.events.BNMRQuirkKeyEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class KeyUsagePacket {

    public BNMRKeyBinds.Keys key;

    public KeyUsagePacket(BNMRKeyBinds.Keys key) {
        this.key = key;
    }

    public KeyUsagePacket(ByteBuf buf) {
        this.key = BNMRKeyBinds.Keys.getKey(buf.readInt());
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(BNMRKeyBinds.Keys.getKey(this.key));
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                MinecraftForge.EVENT_BUS.post(new BNMRQuirkKeyEvent(player, this.key));
                if(ctx.get().getDirection().getReceptionSide().isServer())
                    BNMRNetworking.instance.sendTo(new KeyUsagePacket(this.key), player.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
