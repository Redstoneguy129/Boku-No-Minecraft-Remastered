package me.redstoneguy129.gillygogs.bokunominecraft.common;

import com.mojang.brigadier.CommandDispatcher;
import me.redstoneguy129.gillygogs.bokunominecraft.common.capabilities.PlayerCapabilityProvider;
import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.Quirk;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

public class BNMRCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        BNMRCommands commands = new BNMRCommands();
        dispatcher.register(Commands.literal("mha").requires(plr -> plr.hasPermissionLevel(2))
                .then(Commands.literal("quirk")
                        .then(Commands.argument("players", EntityArgument.player())
                            .then(Commands.argument("quirks", ResourceLocationArgument.resourceLocation())
                                .executes(c -> commands.setQuirk(EntityArgument.getPlayer(c, "players"), ResourceLocationArgument.getResourceLocation(c, "quirks"))))))
        );
    }

    private int setQuirk(ServerPlayerEntity serverPlayerEntity, ResourceLocation quirkLocation) {
        Quirk quirk = Quirk.QUIRK.getValue(quirkLocation);
        serverPlayerEntity.getCapability(PlayerCapabilityProvider.CAPABILITY).ifPresent(iPlayerCapability -> iPlayerCapability.setQuirk(quirk));
        return 1;
    }
}
