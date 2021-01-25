package me.redstoneguy129.gillygogs.bokunominecraft.common.objects.quirks;

import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.Quirk;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AirWalkQuirk extends Quirk {

    @Override
    public void onActivated(PlayerEntity playerEntity) {
        playerEntity.abilities.allowFlying = true;
    }

    @Override
    public void onUpdate(PlayerEntity playerEntity) {
        if(playerEntity.abilities.isFlying) {
            if(playerEntity.getPosition().getY() - getBlockBelowLoc(playerEntity.getPosition(), playerEntity.world).getY() > 20)
                playerEntity.abilities.isFlying = false;
        }
    }

    @Override
    public void onDeactivated(PlayerEntity playerEntity) {
        playerEntity.abilities.allowFlying = false;
        playerEntity.abilities.isFlying = false;
    }

    private BlockPos getBlockBelowLoc(BlockPos loc, World world) {
        BlockPos locBelow = loc.down();
        if(world.getBlockState(loc).getMaterial() == Material.AIR) {
            locBelow = getBlockBelowLoc(locBelow, world);
        }
        return locBelow;
    }
}
