package me.ultrusmods.luckyducks.data;

import me.ultrusmods.luckyducks.entity.RubberDuckType;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public class LuckyDucksTrackedData {

	public static final TrackedDataHandler<RubberDuckType> RUBBER_DUCK_TYPE;
	public static void init() {

	}
	static {
		RUBBER_DUCK_TYPE = TrackedDataHandler.createIndexed(RubberDuckRegistry.RUBBER_DUCK_TYPES);
		TrackedDataHandlerRegistry.register(RUBBER_DUCK_TYPE);
	}
}
