package me.ultrusmods.luckyducks.client.render;

import me.ultrusmods.luckyducks.client.LuckyDucksClient;
import me.ultrusmods.luckyducks.client.model.RubberDuckEntityModel;
import me.ultrusmods.luckyducks.entity.RubberDuckEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class RubberDuckEntityRenderer extends MobEntityRenderer<RubberDuckEntity, RubberDuckEntityModel> {

	public RubberDuckEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RubberDuckEntityModel(context.getPart(LuckyDucksClient.RUBBER_DUCK_MODEL_LAYER)), 0.25f);
	}

	@Override
	public Identifier getTexture(RubberDuckEntity entity) {
		return entity.getDuckType().texture();
	}
}
