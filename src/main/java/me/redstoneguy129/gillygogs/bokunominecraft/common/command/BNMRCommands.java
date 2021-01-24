package me.redstoneguy129.gillygogs.bokunominecraft.common.command;

import com.mojang.brigadier.CommandDispatcher;
import me.redstoneguy129.gillygogs.bokunominecraft.common.capabilities.PlayerCapabilityProvider;
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
        serverPlayerEntity.getCapability(PlayerCapabilityProvider.CAPABILITY).ifPresent(iPlayerCapability -> iPlayerCapability.setQuirk(quirk));
        return 1;
    }
}
