package me.redstoneguy129.gillygogs.bokunominecraft.common.objects.quirks;

import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.Quirk;
import net.minecraft.entity.player.PlayerEntity;

public class TailQuirk extends Quirk {

    public TailQuirk() {
        super(Type.MUTANT);
    }

    @Override
    public void onUpdate(PlayerEntity playerEntity) {
        System.out.println("Tail ACTIVE!");
    }
}
