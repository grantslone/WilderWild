package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WildClientMod;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.NewSculkSensorBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class SculkSensorBlockEntityRenderer<T extends NewSculkSensorBlockEntity> implements BlockEntityRenderer<T> {
    private final ModelPart base;
    private final ModelPart ne;
    private final ModelPart nw;
    private final ModelPart se;
    private final ModelPart sw;

    private static float merp = (float) (Math.PI / 180);
    private static float pi = (float) Math.PI;

    RenderLayer SENSOR_LAYER = RenderLayer.getEntityCutout(new Identifier(WilderWild.MOD_ID, "textures/entity/sculk_sensor_tendrils/inactive.png"));
    RenderLayer ACTIVE_SENSOR_LAYER = RenderLayer.getEntityCutout(new Identifier(WilderWild.MOD_ID, "textures/entity/sculk_sensor_tendrils/active_overlay.png"));

    public SculkSensorBlockEntityRenderer(Context ctx) {
        ModelPart root = ctx.getLayerModelPart(WildClientMod.SCULK_SENSOR);
        this.base = root.getChild("base");
        this.sw = root.getChild("sw");
        this.se = root.getChild("se");
        this.nw = root.getChild("nw");
        this.ne = root.getChild("ne");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("base", ModelPartBuilder.create().uv(0,0).cuboid(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F), ModelTransform.of(0.0F,0.0F,0.0F,0,0.0F, 0.0F));
        modelPartData.addChild("ne", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.of(3.0F,8.0F,3.0F,0,-0.7854F, pi));
        modelPartData.addChild("nw", ModelPartBuilder.create().uv(0,8).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.of(13.0F,8.0F,3.0F,0,0.7854F, pi));
        modelPartData.addChild("se", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.of(3.0F,8.0F,13.0F,0,0.7854F, pi));
        modelPartData.addChild("sw", ModelPartBuilder.create().uv(0,8).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.of(13.0F,8.0F,13.0F,0,-0.7854F, pi));
        return TexturedModelData.of(modelData,64,64);
    }

    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (WilderWild.RENDER_TENDRILS) {
            World world = entity.getWorld();
            boolean worldNull = world != null;
            BlockState blockState = worldNull ? entity.getCachedState() : Blocks.SCULK_SENSOR.getDefaultState();
            Block block = blockState.getBlock();
            if (block instanceof SculkSensorBlock sensor) {
                boolean active = blockState.get(Properties.SCULK_SENSOR_PHASE) == SculkSensorPhase.ACTIVE;
                matrices.push();
                RenderLayer layer = active ? ACTIVE_SENSOR_LAYER : SENSOR_LAYER;
                if (active) {
                    setTendrilPitches(entity.age + tickDelta, tickDelta, entity);
                } else {
                    this.ne.pitch = 0;
                    this.nw.pitch = 0;
                    this.se.pitch = 0;
                    this.sw.pitch = 0;
                }
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(layer);
                this.render(matrices, vertexConsumer, light, overlay);
                matrices.pop();
            }
        }
    }

    private void setTendrilPitches(float animationProgress, float tickDelta, NewSculkSensorBlockEntity entity) {
        float f = getTendrilPitch(entity, tickDelta) * (float)(Math.cos((double)animationProgress * 2.25D) * (25 * merp));
        float g = getTendrilPitch(entity, tickDelta) * (float)(-Math.sin((double)animationProgress * 2.25D) * (25 * merp));

        this.ne.pitch = f;
        this.sw.pitch = f;
        this.nw.pitch = g;
        this.se.pitch = g;
    }

    public float getTendrilPitch(NewSculkSensorBlockEntity entity, float tickDelta) {
        return MathHelper.lerp(tickDelta, (float)entity.prevAnimTicks, (float)entity.animTicks) / 10.0F;
    }

    private void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        this.base.render(matrices, vertices, light, overlay);
        this.ne.render(matrices, vertices, light, overlay);
        this.nw.render(matrices, vertices, light, overlay);
        this.se.render(matrices, vertices, light, overlay);
        this.sw.render(matrices, vertices, light, overlay);
    }
}
