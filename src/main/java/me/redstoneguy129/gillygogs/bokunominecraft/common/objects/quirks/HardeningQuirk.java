package me.redstoneguy129.gillygogs.bokunominecraft.common.objects.quirks;

import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.Quirk;
import net.minecraft.entity.player.PlayerEntity;

public class HardeningQuirk extends Quirk {

    public HardeningQuirk() {
        super(Type.TRANSFORMATION);
    }

    @Override
    public void onUpdate(PlayerEntity playerEntity) {
        System.out.println("Hardening is active");
    }
}
