package eu.dynamics.model;


import eu.dynamics.Core;
import eu.dynamics.utils.AngleUtilities;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import org.lwjgl.opengl.GL11;

public class LayerCloak implements LayerRenderer<AbstractClientPlayer> {
    private final RenderPlayer playerRenderer;
    private final CloakModel cloakModel;
    private final Core core;
    public LayerCloak(RenderPlayer playerRendererIn)
    {
        this.playerRenderer = playerRendererIn;
        this.cloakModel = new CloakModel();
        this.core = new Core();
    }
    @Override
    public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {

        if(core.isActivated && entitylivingbaseIn.hasPlayerInfo() && !entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isWearing(EnumPlayerModelParts.CAPE) && entitylivingbaseIn.getLocationCape() != null){
            cloakModel.ticks(System.nanoTime());
            GlStateManager.pushMatrix();
            this.playerRenderer.bindTexture(entitylivingbaseIn.getLocationCape());
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
            GlStateManager.disableLighting();

            double prevX = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * (double)partialTicks - (entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * (double)partialTicks);
            double prevY = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * (double)partialTicks - (entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * (double)partialTicks);
            double prevZ = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * (double)partialTicks - (entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * (double)partialTicks);
            float prevRenderYaw = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
            float prevCameraYaw = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
            double distanceWalkedModified = entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks;

            final double a = AngleUtilities.a((double)(prevRenderYaw * 3.1415927f / 180.0f));
            final double n2 = -AngleUtilities.b(prevRenderYaw * 3.1415927f / 180.0f);
            final float a2 = AngleUtilities.a((float)prevY * 10.0f, -6.0f, 32.0f);
            float n3 = (float)(prevX * a + prevZ * n2) * 100.0f;
            final float n4 = (float)(prevX * n2 - prevZ * a) * 100.0f;

            if (n3 < 0.0f) {
                n3 = 0.0f;
            }
            if (n3 > 165.0f) {
                n3 = 165.0f;
            }

            float n5 = a2 + (float) (AngleUtilities.a(6.0f * prevCameraYaw)) * (float) (16.0f * distanceWalkedModified);


            GlStateManager.scale(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.translate(0.0f, 0.0f, 2.0f);
            GlStateManager.rotate(6.0f + Math.min(n3 / 2.0f + n5, 90.0f), 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(n4 / 2.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(-n4 / 2.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);

            if(entitylivingbaseIn.isSneaking()) {
                GL11.glTranslated(0, 0.15D, 0);
                GlStateManager.rotate(6.0f + Math.min(n3 / 2.0f + n5 + 30 , 90.0f), 0.0f, 1.0f, 0.0f);
            }

            final float max = Math.max(Math.min(0.0f, n5), -3.0f);
            final float min = Math.min(n3 + n5, 90.0f);


            cloakModel.update(min, max, true);
            cloakModel.renderDynamicCloak();
            GlStateManager.translate(0.0f, 0.0f, 0.125f);
            GlStateManager.rotate(6.0f + n3 / 2.0f + n5, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(n4 / 2.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(-n4 / 2.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            GL11.glPopMatrix();

            GlStateManager.enableLighting();
            cloakModel.deleteData();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
