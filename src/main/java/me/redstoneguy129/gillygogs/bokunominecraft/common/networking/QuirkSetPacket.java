package me.redstoneguy129.gillygogs.bokunominecraft.common.networking;

import com.mojang.authlib.GameProfile;
import me.redstoneguy129.gillygogs.bokunominecraft.BokuNoMinecraft;
import me.redstoneguy129.gillygogs.bokunominecraft.common.capabilities.PlayerCapabilityProvider;
import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.Quirk;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Objects;
import java.util.UUID;
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
            PlayerEntity playerEntity;
            if(ctx.get().getDirection().getReceptionSide().isServer()) {
                playerEntity = ctx.get().getSender();
            } else {
                assert Minecraft.getInstance().world != null;
                playerEntity = (PlayerEntity) Minecraft.getInstance().world.getEntityByID(this.playerEntityId);
            }
            if(playerEntity != null) {
                playerEntity.getCapability(PlayerCapabilityProvider.CAPABILITY).ifPresent(playerCapability -> {
                    BokuNoMinecraft.LOGGER.debug("THIS SHOULD BE RAN ON CLIENT!");
                    playerCapability.setQuirk(quirk);
                });
                if(ctx.get().getDirection().getReceptionSide().isServer())
                    BNMRNetworking.instance.sendTo(new QuirkSetPacket(quirk.getRegistryName(), playerEntityId), ((ServerPlayerEntity) playerEntity).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}