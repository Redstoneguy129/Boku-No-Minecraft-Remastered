package me.redstoneguy129.gillygogs.bokunominecraft.common.events;

import me.redstoneguy129.gillygogs.bokunominecraft.client.BNMRKeyBinds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

public class BNMRQuirkKeyEvent extends Event {

    private final PlayerEntity playerEntity;
    private final BNMRKeyBinds.Keys key;
    private final LogicalSide side;

    public BNMRQuirkKeyEvent(PlayerEntity playerEntity, BNMRKeyBinds.Keys key) {
        this.playerEntity = playerEntity;
        this.key = key;
        this.side = playerEntity.world.isRemote ? LogicalSide.CLIENT : LogicalSide.SERVER;
    }

    public PlayerEntity getPlayerEntity() {
        return this.playerEntity;
    }

    public BNMRKeyBinds.Keys getKey() {
        return this.key;
    }

    public LogicalSide getSide() {
        return side;
    }
}