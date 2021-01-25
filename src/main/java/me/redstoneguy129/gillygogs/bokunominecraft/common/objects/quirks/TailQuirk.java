package me.redstoneguy129.gillygogs.bokunominecraft.common.objects.quirks;

import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.Quirk;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class TailQuirk extends Quirk {

    public TailQuirk() {
        super(Type.MUTANT);
    }

    @Override
    public void onActivated(PlayerEntity playerEntity) {
        this.setAttribute(playerEntity, Attributes.ATTACK_DAMAGE, UUID.nameUUIDFromBytes(this.toString().getBytes()), 20F, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void onDeactivated(PlayerEntity playerEntity) {
        this.setAttribute(playerEntity, Attributes.ATTACK_DAMAGE, UUID.nameUUIDFromBytes(this.toString().getBytes()), -20F, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void onUpdate(PlayerEntity playerEntity) {
        System.out.println("Tail ACTIVE!");
    }
}
