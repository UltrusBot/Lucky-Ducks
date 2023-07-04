package me.ultrusmods.luckyducks.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import me.ultrusmods.luckyducks.client.model.RubberDuckEntityModel;
import me.ultrusmods.luckyducks.data.OverlayData;
import me.ultrusmods.luckyducks.entity.RubberDuckEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DuckOverlayFeatureRenderer<T extends RubberDuckEntity> extends FeatureRenderer<T, RubberDuckEntityModel<T>> {
	public DuckOverlayFeatureRenderer(FeatureRendererContext<T, RubberDuckEntityModel<T>> context) {
		super(context);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (entity.getDuckType().overlayData().isPresent()) {
			OverlayData overlayData = entity.getDuckType().overlayData().get();
			Identifier texture = overlayData.texture();
			VertexConsumer vertexConsumer = overlayData.glows() ? vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(texture)) : vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(texture));
			this.getContextModel().render(matrices, vertexConsumer, light, LivingEntityRenderer.getOverlay(entity, 0.0F), 1.0F, 1.0F, 1.0F, overlayData.applyAnimationFunction(tickDelta, animationProgress));
		}
	}
}
