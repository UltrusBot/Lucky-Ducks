package me.ultrusmods.luckyducks.client.model;

import com.mojang.blaze3d.vertex.VertexConsumer;
import me.ultrusmods.luckyducks.entity.RubberDuckEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class RubberDuckEntityModel extends EntityModel<RubberDuckEntity> {
	private final ModelPart duck;
	public RubberDuckEntityModel(ModelPart modelPart) {
		this.duck = modelPart.getChild("duck");
	}
	@Override
	public void setAngles(RubberDuckEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		duck.render(matrices, vertices, light, overlay, red, blue, green, alpha);
	}
}
