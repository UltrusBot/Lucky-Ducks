package me.ultrusmods.luckyducks.data;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum OverlayAnimationType implements StringIdentifiable {
	STATIC("static", (tickDelta, animationProgress, animationSpeed) -> 1.0F),
	BLINK("blink", (tickDelta, animationProgress, animationSpeed) -> (float) Math.floor(Math.sin((animationProgress/50.0F) * animationSpeed) + 1.0F)),
	SIN("sin", (tickDelta, animationProgress, animationSpeed) -> (float) (Math.sin(animationProgress/50.0F) + 1.0f) * animationSpeed),
	PULSE("pulse", (tickDelta, animationProgress, animationSpeed) -> ((animationProgress/50.0F) % 1.0F) * animationSpeed);
	public static final Codec<OverlayAnimationType> CODEC = StringIdentifiable.createCodec(OverlayAnimationType::values);

	private final String name;
	private final OverlayFunction function;

	OverlayAnimationType(String name, OverlayFunction function) {
		this.name = name;
		this.function = function;
	}

	public float  applyFunction(float tickDelta, float animationProgress, float animationSpeed) {
		return this.function.execute(tickDelta, animationProgress, animationSpeed);
	}
	@FunctionalInterface
	public interface OverlayFunction {
		float execute(float tickDelta, float animationProgress, float animationSpeed);
	}

	@Override
	public String asString() {
		return this.name;
	}
}
