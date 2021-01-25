package me.redstoneguy129.gillygogs.bokunominecraft.common.command;

import com.mojang.brigadier.CommandDispatcher;
import me.redstoneguy129.gillygogs.bokunominecraft.common.networking.BNMRNetworking;
import me.redstoneguy129.gillygogs.bokunominecraft.common.networking.QuirkSetPacket;
import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.Quirk;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

public class BNMRCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        BNMRCommands commands = new BNMRCommands();
        dispatcher.register(Commands.literal("mha").requires(plr -> plr.hasPermissionLevel(2))
                .then(Commands.literal("quirk")
                        .then(Commands.argument("player", EntityArgument.player())
                            .then(Commands.argument("quirk", QuirkArgument.quirk())
                                .executes(c -> commands.setQuirk(EntityArgument.getPlayer(c, "player"), QuirkArgument.getQuirk(c, "quirk")))))));
    }

    private int setQuirk(ServerPlayerEntity serverPlayerEntity, Quirk quirk) {
        BNMRNetworking.instance.sendToServer(new QuirkSetPacket(quirk.getRegistryName(), serverPlayerEntity.getEntityId()));
        return 1;
    }
}
