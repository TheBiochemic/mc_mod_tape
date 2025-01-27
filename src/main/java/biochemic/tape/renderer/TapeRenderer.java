package biochemic.tape.renderer;

import org.lwjgl.opengl.GL11;

import biochemic.tape.tileentity.TileEntityTape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class TapeRenderer extends TileEntitySpecialRenderer<TileEntityTape> {

    private static final ResourceLocation texture = new ResourceLocation("tape:textures/entity/tape.png");
    private static final double textureStep = 1.0F / 7.0F;
    private static final double thickness = 1.0F / 64.0F;

    @Override
    public boolean isGlobalRenderer(TileEntityTape tileEntity) {
        return false;
    }

    @Override
    public void render(
        TileEntityTape tileEntity, 
        double relativeX, 
        double relativeY, 
        double relativeZ,
        float partialTicks, 
        int blockDamageProgress, 
        float alpha) {

        if (!(tileEntity instanceof TileEntityTape)) return;
        TileEntityTape tileEntityTape = tileEntity;

        try {
            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableBlend();

            this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            if (Minecraft.isAmbientOcclusionEnabled()) {
                GlStateManager.shadeModel(GL11.GL_SMOOTH);
            } else {
                GlStateManager.shadeModel(GL11.GL_FLAT);
            }

            GlStateManager.translate(relativeX, relativeY, relativeZ);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            this.bindTexture(texture);

            GL11.glDepthMask(true);
            bufferBuilder.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);

            //Setup the triangles for bottom
            this.buildSingleFaceBottom(bufferBuilder, tileEntityTape.bottom.getVerticalBackLeft(), 0.0, 0.0);
            this.buildSingleFaceBottom(bufferBuilder, tileEntityTape.bottom.getVerticalBackRight(), 0.0, 0.5);
            this.buildSingleFaceBottom(bufferBuilder, tileEntityTape.bottom.getVerticalFrontLeft(), 0.5, 0.0);
            this.buildSingleFaceBottom(bufferBuilder, tileEntityTape.bottom.getVerticalFrontRight(), 0.5, 0.5);

            this.buildSingleFaceTop(bufferBuilder, tileEntityTape.top.getVerticalBackLeft(), 0.0, 0.0);
            this.buildSingleFaceTop(bufferBuilder, tileEntityTape.top.getVerticalBackRight(), 0.0, 0.5);
            this.buildSingleFaceTop(bufferBuilder, tileEntityTape.top.getVerticalFrontLeft(), 0.5, 0.0);
            this.buildSingleFaceTop(bufferBuilder, tileEntityTape.top.getVerticalFrontRight(), 0.5, 0.5);

            this.buildSingleFaceBack(bufferBuilder, tileEntityTape.back.getHorizontalBottomLeft(), 0.0, 0.5);
            this.buildSingleFaceBack(bufferBuilder, tileEntityTape.back.getHorizontalTopLeft(), 0.0, 0.0);
            this.buildSingleFaceBack(bufferBuilder, tileEntityTape.back.getHorizontalBottomRight(), 0.5, 0.5);
            this.buildSingleFaceBack(bufferBuilder, tileEntityTape.back.getHorizontalTopRight(), 0.5, 0.0);

            this.buildSingleFaceFront(bufferBuilder, tileEntityTape.front.getHorizontalBottomLeft(), 0.0, 0.5);
            this.buildSingleFaceFront(bufferBuilder, tileEntityTape.front.getHorizontalTopLeft(), 0.0, 0.0);
            this.buildSingleFaceFront(bufferBuilder, tileEntityTape.front.getHorizontalBottomRight(), 0.5, 0.5);
            this.buildSingleFaceFront(bufferBuilder, tileEntityTape.front.getHorizontalTopRight(), 0.5, 0.0);

            this.buildSingleFaceLeft(bufferBuilder, tileEntityTape.left.getHorizontalBottomLeft(), 0.5, 0.0);
            this.buildSingleFaceLeft(bufferBuilder, tileEntityTape.left.getHorizontalTopLeft(), 0.0, 0.0);
            this.buildSingleFaceLeft(bufferBuilder, tileEntityTape.left.getHorizontalBottomRight(), 0.5, 0.5);
            this.buildSingleFaceLeft(bufferBuilder, tileEntityTape.left.getHorizontalTopRight(), 0.0, 0.5);

            this.buildSingleFaceRight(bufferBuilder, tileEntityTape.right.getHorizontalBottomLeft(), 0.5, 0.0);
            this.buildSingleFaceRight(bufferBuilder, tileEntityTape.right.getHorizontalTopLeft(), 0.0, 0.0);
            this.buildSingleFaceRight(bufferBuilder, tileEntityTape.right.getHorizontalBottomRight(), 0.5, 0.5);
            this.buildSingleFaceRight(bufferBuilder, tileEntityTape.right.getHorizontalTopRight(), 0.0, 0.5);
            
            tessellator.draw();

        } finally {
            GlStateManager.popMatrix();
        }

    }

    private void buildSingleFaceTop(BufferBuilder bufferBuilder, byte index, double x, double y) {
        if (index == 0) return;

        double tx = (((double)index - 1) + x) * textureStep;
        double tx2 = tx + textureStep * 0.5;

        bufferBuilder.pos(x, 1.0 - thickness, y).tex(tx, y).endVertex();
        bufferBuilder.pos(x + 0.5, 1.0 - thickness, y).tex(tx2, y).endVertex();
        bufferBuilder.pos(x, 1.0 - thickness, y + 0.5).tex(tx, y + 0.5).endVertex();

        bufferBuilder.pos(x + 0.5, 1.0 - thickness, y + 0.5).tex(tx2, y + 0.5).endVertex();
        bufferBuilder.pos(x, 1.0 - thickness, y + 0.5).tex(tx, y + 0.5).endVertex();
        bufferBuilder.pos(x + 0.5, 1.0 - thickness, y).tex(tx2, y).endVertex();
    }

    private void buildSingleFaceBottom(BufferBuilder bufferBuilder, byte index, double x, double y) {
        if (index == 0) return;

        double tx = (((double)index - 1) + x) * textureStep;
        double tx2 = tx + textureStep * 0.5;

        bufferBuilder.pos(x, thickness, y).tex(tx, y).endVertex();
        bufferBuilder.pos(x, thickness, y + 0.5).tex(tx, y + 0.5).endVertex();
        bufferBuilder.pos(x + 0.5, thickness, y).tex(tx2, y).endVertex();

        bufferBuilder.pos(x + 0.5, thickness, y + 0.5).tex(tx2, y + 0.5).endVertex();
        bufferBuilder.pos(x + 0.5, thickness, y).tex(tx2, y).endVertex();
        bufferBuilder.pos(x, thickness, y + 0.5).tex(tx, y + 0.5).endVertex();
    }

    private void buildSingleFaceBack(BufferBuilder bufferBuilder, byte index, double x, double y) {
        if (index == 0) return;

        double tx = (((double)index - 1) + x) * textureStep;
        double tx2 = tx + textureStep * 0.5;

        bufferBuilder.pos(x, y, 1.0 - thickness).tex(tx, y).endVertex();
        bufferBuilder.pos(x, y + 0.5, 1.0 - thickness).tex(tx, y + 0.5).endVertex();
        bufferBuilder.pos(x + 0.5, y, 1.0 - thickness).tex(tx2, y).endVertex();

        bufferBuilder.pos(x + 0.5, y + 0.5, 1.0 - thickness).tex(tx2, y + 0.5).endVertex();
        bufferBuilder.pos(x + 0.5, y, 1.0 - thickness).tex(tx2, y).endVertex();
        bufferBuilder.pos(x, y + 0.5, 1.0 - thickness).tex(tx, y + 0.5).endVertex();
    }

    private void buildSingleFaceFront(BufferBuilder bufferBuilder, byte index, double x, double y) {
        if (index == 0) return;

        double tx = (((double)index - 1) + x) * textureStep;
        double tx2 = tx + textureStep * 0.5;

        bufferBuilder.pos(x, y, thickness).tex(tx, y).endVertex();
        bufferBuilder.pos(x + 0.5, y, thickness).tex(tx2, y).endVertex();
        bufferBuilder.pos(x, y + 0.5, thickness).tex(tx, y + 0.5).endVertex();

        bufferBuilder.pos(x + 0.5, y + 0.5, thickness).tex(tx2, y + 0.5).endVertex();
        bufferBuilder.pos(x, y + 0.5, thickness).tex(tx, y + 0.5).endVertex();
        bufferBuilder.pos(x + 0.5, y, thickness).tex(tx2, y).endVertex();
    }

    private void buildSingleFaceLeft(BufferBuilder bufferBuilder, byte index, double x, double y) {
        if (index == 0) return;

        double tx = (((double)index - 1) + x) * textureStep;
        double tx2 = tx + textureStep * 0.5;

        bufferBuilder.pos(1.0 - thickness, x, y).tex(tx, y).endVertex();
        bufferBuilder.pos(1.0 - thickness, x, y + 0.5).tex(tx, y + 0.5).endVertex();
        bufferBuilder.pos(1.0 - thickness, x + 0.5, y).tex(tx2, y).endVertex();

        bufferBuilder.pos(1.0 - thickness, x + 0.5, y + 0.5).tex(tx2, y + 0.5).endVertex();
        bufferBuilder.pos(1.0 - thickness, x + 0.5, y).tex(tx2, y).endVertex();
        bufferBuilder.pos(1.0 - thickness, x, y + 0.5).tex(tx, y + 0.5).endVertex();
    }

    private void buildSingleFaceRight(BufferBuilder bufferBuilder, byte index, double x, double y) {
        if (index == 0) return;

        double tx = (((double)index - 1) + x) * textureStep;
        double tx2 = tx + textureStep * 0.5;

        bufferBuilder.pos(thickness, x, y).tex(tx, y).endVertex();
        bufferBuilder.pos(thickness, x + 0.5, y).tex(tx2, y).endVertex();
        bufferBuilder.pos(thickness, x, y + 0.5).tex(tx, y + 0.5).endVertex();

        bufferBuilder.pos(thickness, x + 0.5, y + 0.5).tex(tx2, y + 0.5).endVertex();
        bufferBuilder.pos(thickness, x, y + 0.5).tex(tx, y + 0.5).endVertex();
        bufferBuilder.pos(thickness, x + 0.5, y).tex(tx2, y).endVertex();
    }

}
