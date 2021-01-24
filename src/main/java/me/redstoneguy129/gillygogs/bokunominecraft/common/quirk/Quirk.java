package me.redstoneguy129.gillygogs.bokunominecraft.common.quirk;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.redstoneguy129.gillygogs.bokunominecraft.BokuNoMinecraft;
import me.redstoneguy129.gillygogs.bokunominecraft.client.BNMRKeyBinds;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.Objects;

public class Quirk extends ForgeRegistryEntry<Quirk> {
    public static final IForgeRegistry<Quirk> QUIRK = new RegistryBuilder<Quirk>().setIDRange(0, 2048).setType(Quirk.class).setName(new ResourceLocation(BokuNoMinecraft.MOD_ID, "quirk")).create();

    public boolean activated = false;
    private final Type quirkType;

    public Quirk(Type quirkType) {
        this.quirkType = quirkType;
    }

    public Quirk() {
        this.quirkType = Type.EMITTER;
    }

    public Type getQuirkType() {
        return quirkType;
    }

    public enum Type {
        EMITTER,
        TRANSFORMATION,
        MUTANT
    }

    public void onActivated(PlayerEntity playerEntity) {}

    public void onDeactivated(PlayerEntity playerEntity) {}

    public void onUpdate(PlayerEntity playerEntity) {}

    public void keyUsage(PlayerEntity playerEntity, BNMRKeyBinds.Keys key, LogicalSide side) {}

    @OnlyIn(Dist.CLIENT)
    public void render(AbstractClientPlayerEntity player, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {}

    @OnlyIn(Dist.CLIENT)
    public void render(RenderPlayerEvent.Pre event) {}

    @Override
    public String toString() {
        return Objects.requireNonNull(this.getRegistryName()).toString();
    }
}
