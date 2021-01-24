package me.redstoneguy129.gillygogs.bokunominecraft.common.quirk;

import me.redstoneguy129.gillygogs.bokunominecraft.client.renderer.QuirkLayerRenderer;
import me.redstoneguy129.gillygogs.bokunominecraft.common.capabilities.PlayerCapabilityProvider;
import me.redstoneguy129.gillygogs.bokunominecraft.common.events.BNMRQuirkKeyEvent;
import me.redstoneguy129.gillygogs.bokunominecraft.common.networking.BNMRNetworking;
import me.redstoneguy129.gillygogs.bokunominecraft.common.networking.QuirkSetPacket;
import me.redstoneguy129.gillygogs.bokunominecraft.common.networking.QuirkTogglePacket;
import me.redstoneguy129.gillygogs.bokunominecraft.common.objects.quirks.BNMRQuirks;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuirkEvents {

    @SubscribeEvent
    public void giveQuirkOnJoin(EntityJoinWorldEvent event) {
        if(!(event.getEntity() instanceof PlayerEntity)) return;
        PlayerEntity playerEntity = (PlayerEntity) event.getEntity();
        playerEntity.getCapability(PlayerCapabilityProvider.CAPABILITY).ifPresent(playerCapability -> {
            System.out.println("RUNNING");
            if(!playerCapability.hasLoggedInBefore()) {
                playerCapability.setLoggedInBefore(true);
                if(event.getWorld().isRemote()) {
                    Quirk quirk = BNMRQuirks.DARK_SHADOW.get();
                    playerCapability.setQuirk(quirk);
                    BNMRNetworking.instance.sendToServer(new QuirkSetPacket(quirk.getRegistryName(), playerEntity.getEntityId()));
                }
            }
        });
    }

    @SubscribeEvent
    public void updateEvent(LivingEvent.LivingUpdateEvent event) {
        if(event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            playerEntity.getCapability(PlayerCapabilityProvider.CAPABILITY).ifPresent(playerCapability -> {
                if(playerCapability.getQuirk() != null) {
                    if(playerCapability.getQuirk().activated) {
                        playerCapability.getQuirk().onUpdate(playerEntity);
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public void keyEvent(BNMRQuirkKeyEvent event) {
        event.getPlayerEntity().getCapability(PlayerCapabilityProvider.CAPABILITY).ifPresent(playerCapability -> {
            if(playerCapability.getQuirk() != null && playerCapability.getQuirk().activated) {
                playerCapability.getQuirk().keyUsage(event.getPlayerEntity(), event.getKey(), event.getSide());
            }
        });
    }

    private final List<PlayerRenderer> playerRendererList = new ArrayList<>();

    @SubscribeEvent
    public void render(RenderPlayerEvent.Pre event) {
        event.getPlayer().getCapability(PlayerCapabilityProvider.CAPABILITY).ifPresent(playerCapability -> {
            if(playerCapability.getQuirk() != null && playerCapability.getQuirk().getQuirkType().equals(Quirk.Type.MUTANT)) {
                event.setCanceled(true);
                playerCapability.getQuirk().render(event);
            }
        });
        if(playerRendererList.contains(event.getRenderer())) return;
        event.getRenderer().addLayer(new QuirkLayerRenderer(event.getRenderer()));
        playerRendererList.add(event.getRenderer());
    }
}
