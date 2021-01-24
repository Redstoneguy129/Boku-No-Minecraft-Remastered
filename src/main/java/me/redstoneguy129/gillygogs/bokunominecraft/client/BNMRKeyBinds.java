package me.redstoneguy129.gillygogs.bokunominecraft.client;

import me.redstoneguy129.gillygogs.bokunominecraft.BokuNoMinecraft;
import me.redstoneguy129.gillygogs.bokunominecraft.common.networking.BNMRNetworking;
import me.redstoneguy129.gillygogs.bokunominecraft.common.networking.KeyUsagePacket;
import me.redstoneguy129.gillygogs.bokunominecraft.common.networking.QuirkTogglePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class BNMRKeyBinds {

    private static final String CATEGORY = "key.categories."+ BokuNoMinecraft.MOD_ID;

    public static final KeyBinding KEY_1 = new KeyBinding(BokuNoMinecraft.MOD_ID+".key.1", GLFW.GLFW_KEY_Z, CATEGORY);
    public static final KeyBinding KEY_2 = new KeyBinding(BokuNoMinecraft.MOD_ID+".key.2", GLFW.GLFW_KEY_R, CATEGORY);
    public static final KeyBinding KEY_3 = new KeyBinding(BokuNoMinecraft.MOD_ID+".key.3", GLFW.GLFW_KEY_G, CATEGORY);
    public static final KeyBinding KEY_4 = new KeyBinding(BokuNoMinecraft.MOD_ID+".key.4", GLFW.GLFW_KEY_V, CATEGORY);
    public static final KeyBinding KEY_5 = new KeyBinding(BokuNoMinecraft.MOD_ID+".key.5", GLFW.GLFW_KEY_B, CATEGORY);

    public static final KeyBinding TOGGLE_QUIRK = new KeyBinding(BokuNoMinecraft.MOD_ID+".key.toggle_quirk", GLFW.GLFW_KEY_X, CATEGORY);

    static {
        ClientRegistry.registerKeyBinding(KEY_1);
        ClientRegistry.registerKeyBinding(KEY_2);
        ClientRegistry.registerKeyBinding(KEY_3);
        ClientRegistry.registerKeyBinding(KEY_4);
        ClientRegistry.registerKeyBinding(KEY_5);
        ClientRegistry.registerKeyBinding(TOGGLE_QUIRK);
    }

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event) {
        assert Minecraft.getInstance().player != null;
        if(KEY_1.isPressed()) {
            BNMRNetworking.instance.sendToServer(new KeyUsagePacket(Keys.FIRST));
        } else if(KEY_2.isPressed()) {
            BNMRNetworking.instance.sendToServer(new KeyUsagePacket(Keys.SECOND));
        } else if(KEY_3.isPressed()) {
            BNMRNetworking.instance.sendToServer(new KeyUsagePacket(Keys.THIRD));
        } else if(KEY_4.isPressed()) {
            BNMRNetworking.instance.sendToServer(new KeyUsagePacket(Keys.FOURTH));
        } else if(KEY_5.isPressed()) {
            BNMRNetworking.instance.sendToServer(new KeyUsagePacket(Keys.FIFTH));
        } else if(TOGGLE_QUIRK.isPressed()) {
            BNMRNetworking.instance.sendToServer(new QuirkTogglePacket(Minecraft.getInstance().player.getEntityId()));
        }
    }

    public enum Keys {
        FIRST,
        SECOND,
        THIRD,
        FOURTH,
        FIFTH;

        public static Keys getKey(int key) {
            return Keys.values()[key];
        }

        public static int getKey(Keys key) {
            int i = 0;
            for(Keys keys : Keys.values()) {
                if(keys == key) return i;
                i++;
            }
            return 0;
        }
    }
}
