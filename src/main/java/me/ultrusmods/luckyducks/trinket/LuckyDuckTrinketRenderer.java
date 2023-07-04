package me.ultrusmods.luckyducks.trinket;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Axis;

public class LuckyDuckTrinketRenderer implements TrinketRenderer {

	@SuppressWarnings("unchecked")
	@Override
	public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (!(entity instanceof AbstractClientPlayerEntity playerEntity)) return;
		TrinketRenderer.translateToFace(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, playerEntity, headYaw, headPitch);
		matrices.translate(0.0D, 0.0D, 0.25D);
		matrices.multiply(Axis.Y_POSITIVE.rotationDegrees(180.0F));
		matrices.scale(0.625F, -0.625F, -0.625F);
		MinecraftClient.getInstance().getItemRenderer().renderItem(playerEntity, stack, ModelTransformationMode.HEAD, false, matrices, vertexConsumers, playerEntity.getWorld(), light, OverlayTexture.DEFAULT_UV, entity.getId() + ModelTransformationMode.HEAD.ordinal());

	}
}
