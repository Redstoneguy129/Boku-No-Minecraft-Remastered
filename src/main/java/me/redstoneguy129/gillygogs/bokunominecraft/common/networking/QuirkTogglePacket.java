package me.redstoneguy129.gillygogs.bokunominecraft.common.networking;

import me.redstoneguy129.gillygogs.bokunominecraft.BokuNoMinecraft;
import me.redstoneguy129.gillygogs.bokunominecraft.common.capabilities.PlayerCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class QuirkTogglePacket {

    private final int playerEntityId;

    public QuirkTogglePacket(int playerEntityId) {
        this.playerEntityId = playerEntityId;
    }

    public QuirkTogglePacket(PacketBuffer buf) {
        this.playerEntityId = buf.readInt();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(this.playerEntityId);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender();
            if(ctx.get().getDirection().getReceptionSide().isClient()) {
                assert Minecraft.getInstance().world != null;
                player = (PlayerEntity) Minecraft.getInstance().world.getEntityByID(this.playerEntityId);
            }
            if (player != null) {
                PlayerEntity finalPlayer = player;
                player.getCapability(PlayerCapabilityProvider.CAPABILITY).ifPresent(playerCapability -> {
                    if(playerCapability.getQuirk() != null) {
                        BokuNoMinecraft.LOGGER.debug("--------------");
                        System.out.println(playerCapability.getQuirk().activated);
                        if(ctx.get().getDirection().getReceptionSide().isServer()) playerCapability.getQuirk().activated = !playerCapability.getQuirk().activated;
                        System.out.println(playerCapability.getQuirk().activated);
                        BokuNoMinecraft.LOGGER.debug("--------------");
                        if(playerCapability.getQuirk().activated)
                            playerCapability.getQuirk().onActivated(finalPlayer);
                        else playerCapability.getQuirk().onDeactivated(finalPlayer);
                    }
                });
                if(ctx.get().getDirection().getReceptionSide().isServer())
                    BNMRNetworking.instance.sendTo(new QuirkTogglePacket(this.playerEntityId), ((ServerPlayerEntity) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}