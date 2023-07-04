package me.ultrusmods.luckyducks.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

/**
 *
 * @param texture Location of the texture for the overlay
 * @param glows Whether the overlay should glow, like glow squids.
 * @param animationType The type of animation the alpha channel uses, such as static, or pulse.
 * @param animationSpeed The speed that the animation speed is multiplied by.
 * @param animationOffset The offset added to the animation progress.
 */
public record OverlayData(Identifier texture, boolean glows, OverlayAnimationType animationType, float animationSpeed, float animationOffset) {
	public static Codec<OverlayData> CODEC = RecordCodecBuilder.create(builder -> builder.group(
		Identifier.CODEC.fieldOf("texture").forGetter(OverlayData::texture),
		Codec.BOOL.optionalFieldOf("glows", false).forGetter(OverlayData::glows),
		OverlayAnimationType.CODEC.optionalFieldOf("animation_type", OverlayAnimationType.STATIC).forGetter(OverlayData::animationType),
		Codec.FLOAT.optionalFieldOf("animation_speed", 1.0f).forGetter(OverlayData::animationSpeed),
		Codec.FLOAT.optionalFieldOf("animation_offset", 0.0f).forGetter(OverlayData::animationOffset)
	).apply(builder, OverlayData::new));

	public float applyAnimationFunction(float tickDelta, float animationProgress) {
		return Math.min(Math.max(this.animationType().applyFunction(tickDelta, animationProgress, this.animationSpeed()) + this.animationOffset(), 0.0f), 1.0f);
	}
}
