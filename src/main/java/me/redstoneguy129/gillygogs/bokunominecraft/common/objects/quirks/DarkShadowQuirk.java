package me.redstoneguy129.gillygogs.bokunominecraft.common.objects.quirks;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.redstoneguy129.gillygogs.bokunominecraft.BokuNoMinecraft;
import me.redstoneguy129.gillygogs.bokunominecraft.client.models.DarkShadowModel;
import me.redstoneguy129.gillygogs.bokunominecraft.common.quirk.Quirk;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class DarkShadowQuirk extends Quirk {

    public DarkShadowQuirk() {
        super(Type.TRANSFORMATION);
    }

    @Override
    public void onUpdate(PlayerEntity playerEntity) {
        System.out.println("Test Dark Shadow");
    }

    @Override
    public void render(AbstractClientPlayerEntity player, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        DarkShadowModel darkShadowModel = new DarkShadowModel();
        ResourceLocation darkShadowTexture = new ResourceLocation(BokuNoMinecraft.MOD_ID, "textures/layer/dark_shadow.png");
        BokuNoMinecraft.LOGGER.debug("Render Start");
        darkShadowModel.render(matrixStack, buffer.getBuffer(RenderType.getEntityTranslucent(darkShadowTexture)), packedLight, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
        BokuNoMinecraft.LOGGER.debug("Render Finish");
    }
}
