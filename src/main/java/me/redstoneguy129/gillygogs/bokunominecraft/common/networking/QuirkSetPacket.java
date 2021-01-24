package me.redstoneguy129.gillygogs.bokunominecraft.common.networking;

import me.redstoneguy129.gillygogs.bokunominecraft.common.capabilities.PlayerCapabilityProvider;
import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.Quirk;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class QuirkSetPacket {

    private final Quirk quirk;
    private final int playerEntityId;

    public QuirkSetPacket(ResourceLocation quirkLocation, int playerEntityId) {
        this.quirk = Quirk.QUIRK.getValue(quirkLocation);
        this.playerEntityId = playerEntityId;
    }

    public QuirkSetPacket(PacketBuffer buf) {
        this.quirk = Quirk.QUIRK.getValue(buf.readResourceLocation());
        this.playerEntityId = buf.readInt();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeResourceLocation(Objects.requireNonNull(this.quirk.getRegistryName()));
        buf.writeInt(this.playerEntityId);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = ctx.get().getSender();
            if(ctx.get().getDirection().getReceptionSide().isClient()) {
                assert Minecraft.getInstance().world != null;
                player = (PlayerEntity) Minecraft.getInstance().world.getEntityByID(this.playerEntityId);
            }
            if(player != null) {
                player.getCapability(PlayerCapabilityProvider.CAPABILITY).ifPresent(playerCapability -> {
                    playerCapability.setQuirk(quirk);
                });
                if(ctx.get().getDirection().getReceptionSide().isServer())
                    BNMRNetworking.instance.sendTo(new QuirkSetPacket(quirk.getRegistryName(), playerEntityId), ((ServerPlayerEntity) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}