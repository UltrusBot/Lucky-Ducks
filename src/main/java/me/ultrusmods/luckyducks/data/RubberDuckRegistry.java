package me.ultrusmods.luckyducks.data;

import me.ultrusmods.luckyducks.LuckyDucksMod;
import me.ultrusmods.luckyducks.entity.RubberDuckType;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.registry.SimpleRegistry;

public class RubberDuckRegistry {
	public static final SimpleRegistry<RubberDuckType> RUBBER_DUCK_TYPES = FabricRegistryBuilder.createSimple(RubberDuckType.class, LuckyDucksMod.id("rubber_duck_type")).buildAndRegister();

	public static void init() {

	}
}
