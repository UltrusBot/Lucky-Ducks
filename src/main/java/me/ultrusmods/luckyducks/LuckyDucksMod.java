package me.ultrusmods.luckyducks;

import me.ultrusmods.luckyducks.data.LuckyDucksTrackedData;
import me.ultrusmods.luckyducks.data.RubberDuckRegistry;
import me.ultrusmods.luckyducks.entity.RubberDuckEntity;
import me.ultrusmods.luckyducks.entity.RubberDuckType;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuckyDucksMod implements ModInitializer {
	public static final String MOD_ID = "luckyducks";
	public static final Logger LOGGER = LoggerFactory.getLogger("Example Mod");

	public static final EntityType<RubberDuckEntity> RUBBER_DUCK = Registry.register(
			Registry.ENTITY_TYPE,
			id("rubber_duck"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RubberDuckEntity::new).dimensions(EntityDimensions.fixed(0.65f, 0.65f)).build()
	);
	public static final Item RUBBER_DUCK_SPAWN_EGG = Registry.register(Registry.ITEM, id("rubber_duck_spawn_egg"), new SpawnEggItem(RUBBER_DUCK, 0xFFF167, 0xD37400, (new Item.Settings()).group(ItemGroup.MISC)));

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		RubberDuckType.init();
		RubberDuckRegistry.init();
		LuckyDucksTrackedData.init();
		FabricDefaultAttributeRegistry.register(RUBBER_DUCK, RubberDuckEntity.createRubberDuckAttributes());
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
