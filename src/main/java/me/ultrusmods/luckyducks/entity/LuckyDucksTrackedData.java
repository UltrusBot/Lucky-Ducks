package me.ultrusmods.luckyducks.entity;

import me.ultrusmods.luckyducks.data.RubberDuckRegistry;
import me.ultrusmods.luckyducks.entity.RubberDuckType;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public class LuckyDucksTrackedData {

	public static final TrackedDataHandler<RubberDuckType> RUBBER_DUCK_TYPE = TrackedDataHandler.createIndexed(RubberDuckRegistry.RUBBER_DUCK_TYPES);
	public static void init() {
		TrackedDataHandlerRegistry.register(RUBBER_DUCK_TYPE);
	}
}
