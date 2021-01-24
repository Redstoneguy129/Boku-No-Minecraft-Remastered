package me.redstoneguy129.gillygogs.bokunominecraft.common.networking;

import me.redstoneguy129.gillygogs.bokunominecraft.BokuNoMinecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class BNMRNetworking {

    public static SimpleChannel instance;
    private static int ID = 0;

    public static int NextID() {
        return ID++;
    }

    public static void registerMessages() {
        instance = NetworkRegistry.newSimpleChannel(new ResourceLocation(BokuNoMinecraft.MOD_ID, "networking"), () -> "1.0", s -> true, s -> true);
        instance.registerMessage(NextID(), KeyUsagePacket.class, KeyUsagePacket::toBytes, KeyUsagePacket::new, KeyUsagePacket::handle);
        instance.registerMessage(NextID(), QuirkTogglePacket.class, QuirkTogglePacket::toBytes, QuirkTogglePacket::new, QuirkTogglePacket::handle);
        instance.registerMessage(NextID(), QuirkSetPacket.class, QuirkSetPacket::toBytes, QuirkSetPacket::new, QuirkSetPacket::handle);
    }
}
