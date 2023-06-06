package me.ultrusmods.luckyducks.entity;

import me.ultrusmods.luckyducks.LuckyDucksMod;
import me.ultrusmods.luckyducks.data.RubberDuckRegistry;
import net.minecraft.entity.data.TrackedDataHandler;
import org.quiltmc.qsl.entity.networking.api.tracked_data.QuiltTrackedDataHandlerRegistry;

public class LuckyDucksTrackedData {

	public static final TrackedDataHandler<RubberDuckType> RUBBER_DUCK_TYPE = TrackedDataHandler.createIndexed(RubberDuckRegistry.RUBBER_DUCK_TYPES);
	public static void init() {
		QuiltTrackedDataHandlerRegistry.register(LuckyDucksMod.id("rubber_duck_type"), RUBBER_DUCK_TYPE);
	}
}
