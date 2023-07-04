package me.ultrusmods.luckyducks.client;

import me.ultrusmods.luckyducks.LuckyDucksMod;
import me.ultrusmods.luckyducks.client.model.RubberDuckEntityModel;
import me.ultrusmods.luckyducks.client.render.RubberDuckEntityRenderer;
import me.ultrusmods.luckyducks.client.render.RubberDuckItemRenderer;
import me.ultrusmods.luckyducks.trinket.TrinketsCompat;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class LuckyDucksClient implements ClientModInitializer {
	public static final EntityModelLayer RUBBER_DUCK_MODEL_LAYER = new EntityModelLayer(LuckyDucksMod.id("rubber_duck"), "main");
	@Override
	public void onInitializeClient(ModContainer mod) {
		EntityModelLayerRegistry.registerModelLayer(RUBBER_DUCK_MODEL_LAYER, RubberDuckEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(LuckyDucksMod.RUBBER_DUCK, RubberDuckEntityRenderer::new);
		BuiltinItemRendererRegistry.INSTANCE.register(LuckyDucksMod.RUBBER_DUCK_ITEM, new RubberDuckItemRenderer());
		if (LuckyDucksMod.TRINKETS_LOADED) {
			TrinketsCompat.registerRenderers();
		}
	}
}
