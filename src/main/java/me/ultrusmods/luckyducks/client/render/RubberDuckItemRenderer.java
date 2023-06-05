package me.ultrusmods.luckyducks.client.render;

import me.ultrusmods.luckyducks.LuckyDucksMod;
import me.ultrusmods.luckyducks.data.RubberDuckRegistry;
import me.ultrusmods.luckyducks.entity.RubberDuckEntity;
import me.ultrusmods.luckyducks.entity.RubberDuckType;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class RubberDuckItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
	@Override
	public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
		RubberDuckEntity rubberDuck = new RubberDuckEntity(LuckyDucksMod.RUBBER_DUCK, MinecraftClient.getInstance().world);
		NbtCompound nbt = stack.getSubNbt("duckEntity");
		if (nbt != null) {
			String typeString = nbt.getString("type");
			if (typeString != "") {
				RubberDuckType type = RubberDuckRegistry.RUBBER_DUCK_TYPES.get(new Identifier(typeString));
				if (type == null) {
					type = RubberDuckType.DEFAULT;
				}
				rubberDuck.setType(type);
			}
		}
		entityRenderDispatcher.render(rubberDuck, 0.0d, 0.0d, 0.0d, 0.0F, 1.0F, matrices, vertexConsumers, light);
	}
}
