package me.redstoneguy129.gillygogs.bokunominecraft.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.redstoneguy129.gillygogs.bokunominecraft.BokuNoMinecraft;
import me.redstoneguy129.gillygogs.bokunominecraft.common.capabilities.PlayerCapabilityProvider;
import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.Quirk;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;

import javax.annotation.Nonnull;

public class QuirkLayerRenderer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    public QuirkLayerRenderer(PlayerRenderer playerRenderer) {
        super(playerRenderer);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int packedLight, AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        player.getCapability(PlayerCapabilityProvider.CAPABILITY).ifPresent(playerCapability -> {
            if(playerCapability.getQuirk() != null && playerCapability.getQuirk().activated && playerCapability.getQuirk().getQuirkType().equals(Quirk.Type.TRANSFORMATION)) {
                BokuNoMinecraft.LOGGER.debug("Layer Render Start");
                playerCapability.getQuirk().render(player, matrixStack, buffer, packedLight, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
                BokuNoMinecraft.LOGGER.debug("Layer Render Finish");
            }
        });
    }
}