package me.redstoneguy129.gillygogs.bokunominecraft;

import me.redstoneguy129.gillygogs.bokunominecraft.client.BNMRKeyBinds;
import me.redstoneguy129.gillygogs.bokunominecraft.common.capabilities.CapabilityEvents;
import me.redstoneguy129.gillygogs.bokunominecraft.common.capabilities.IPlayerCapability;
import me.redstoneguy129.gillygogs.bokunominecraft.common.capabilities.PlayerCapability;
import me.redstoneguy129.gillygogs.bokunominecraft.common.capabilities.PlayerCapabilityStorage;
import me.redstoneguy129.gillygogs.bokunominecraft.common.command.BNMRCommands;
import me.redstoneguy129.gillygogs.bokunominecraft.common.networking.BNMRNetworking;
import me.redstoneguy129.gillygogs.bokunominecraft.common.objects.quirks.BNMRQuirks;
import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.QuirkEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BokuNoMinecraft.MOD_ID)
public class BokuNoMinecraft {

    public static final String MOD_ID = "boku-no-minecraft";
    public static final Logger LOGGER = LogManager.getLogger();

    public BokuNoMinecraft() {
        this.registerObjects(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::commandsSetup);
        MinecraftForge.EVENT_BUS.register(new QuirkEvents());
        MinecraftForge.EVENT_BUS.register(new BNMRKeyBinds());
        MinecraftForge.EVENT_BUS.register(new CapabilityEvents());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerCapability.class, new PlayerCapabilityStorage(), PlayerCapability::new);
        BNMRNetworking.registerMessages();
    }

    private void commandsSetup(final RegisterCommandsEvent event) {
        BNMRCommands.register(event.getDispatcher());
    }

    private void registerObjects(IEventBus eventBus) {
        BNMRQuirks.REGISTERS.register(eventBus);
    }
}
