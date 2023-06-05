package me.ultrusmods.luckyducks.client.model;

import com.mojang.blaze3d.vertex.VertexConsumer;
import me.ultrusmods.luckyducks.entity.RubberDuckEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class RubberDuckEntityModel extends EntityModel<RubberDuckEntity> {
	private final ModelPart duck;
	public RubberDuckEntityModel(ModelPart modelPart) {
		this.duck = modelPart.getChild("duck");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData duck = modelPartData.addChild("duck", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, -3.1416F, 1.5708F, 3.1416F));

		ModelPartData head = duck.addChild("head", ModelPartBuilder.create().uv(0, 10).cuboid(1.0F, 31.0F, -1.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
			.uv(0, 0).cuboid(5.0F, 33.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -38.0F, -1.0F));

		ModelPartData body = duck.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 29.5F, -1.0F, 8.0F, 4.0F, 6.0F, new Dilation(0.0F))
			.uv(16, 14).cuboid(-7.0F, 29.5F, 5.0F, 6.0F, 3.0F, 1.0F, new Dilation(0.0F))
			.uv(12, 10).cuboid(-7.0F, 29.5F, -2.0F, 6.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -33.5F, -2.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(RubberDuckEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		duck.render(matrices, vertices, light, overlay, red, blue, green, alpha);
	}
}
