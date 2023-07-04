package me.ultrusmods.luckyducks.client.render;

import me.ultrusmods.luckyducks.client.LuckyDucksClient;
import me.ultrusmods.luckyducks.client.model.RubberDuckEntityModel;
import me.ultrusmods.luckyducks.entity.RubberDuckEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class RubberDuckEntityRenderer extends MobEntityRenderer<RubberDuckEntity, RubberDuckEntityModel<RubberDuckEntity>> {

	public RubberDuckEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RubberDuckEntityModel<>(context.getPart(LuckyDucksClient.RUBBER_DUCK_MODEL_LAYER)), 0.25f);
		this.addFeature(new DuckOverlayFeatureRenderer<>(this));
	}

	@Override
	public Identifier getTexture(RubberDuckEntity entity) {
		return entity.getDuckType().texture();
	}

	@Nullable
	@Override
	protected RenderLayer getRenderLayer(RubberDuckEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
		return RenderLayer.getEntityTranslucent(this.getTexture(entity), false);
	}
}
