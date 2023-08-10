package me.ultrusmods.luckyducks.client.render;

import me.ultrusmods.luckyducks.LuckyDucksMod;
import me.ultrusmods.luckyducks.data.RubberDuckRegistry;
import me.ultrusmods.luckyducks.data.RubberDuckType;
import me.ultrusmods.luckyducks.entity.RubberDuckEntity;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class RubberDuckItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

	public static final Map<RubberDuckType, RubberDuckEntity> CACHED_DUCKS_FOR_RENDERING = new HashMap<>();
	@Override
	public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
		NbtCompound nbt = stack.getSubNbt("duckEntity");
		if (nbt != null) {
			String typeString = nbt.getString("type");
			if (typeString != "") {
				RubberDuckType type = RubberDuckRegistry.RUBBER_DUCK_TYPES.get(new Identifier(typeString));
				if (type == null) {
					type = RubberDuckType.DEFAULT;
				}
				RubberDuckEntity rubberDuck = CACHED_DUCKS_FOR_RENDERING.get(type);
				if (rubberDuck == null) {
					rubberDuck = new RubberDuckEntity(LuckyDucksMod.RUBBER_DUCK, MinecraftClient.getInstance().world);
					rubberDuck.setType(type);
					rubberDuck.setAiDisabled(true);
					CACHED_DUCKS_FOR_RENDERING.put(type, rubberDuck);
				}
				entityRenderDispatcher.render(rubberDuck, 0.0d, 0.0d, 0.0d, 0.0F, 1.0F, matrices, vertexConsumers, light);
			}
		} else {
			RubberDuckEntity rubberDuck = CACHED_DUCKS_FOR_RENDERING.get(RubberDuckType.DEFAULT);
			if (rubberDuck == null) {
				rubberDuck = addDuckForRendering(RubberDuckType.DEFAULT);
				CACHED_DUCKS_FOR_RENDERING.put(RubberDuckType.DEFAULT, rubberDuck);
			}
			entityRenderDispatcher.render(rubberDuck, 0.0d, 0.0d, 0.0d, 0.0F, 1.0F, matrices, vertexConsumers, light);
		}

	}

	public static RubberDuckEntity addDuckForRendering(RubberDuckType type) {
		RubberDuckEntity rubberDuck = new RubberDuckEntity(LuckyDucksMod.RUBBER_DUCK, MinecraftClient.getInstance().world);
		rubberDuck.setType(type);
		rubberDuck.setAiDisabled(true);
		CACHED_DUCKS_FOR_RENDERING.put(type, rubberDuck);
		return rubberDuck;
	}
}
