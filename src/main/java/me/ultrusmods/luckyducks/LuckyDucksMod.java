package me.ultrusmods.luckyducks;

import me.ultrusmods.luckyducks.block.RubberDuckDispenserBehavior;
import me.ultrusmods.luckyducks.data.DucksLoader;
import me.ultrusmods.luckyducks.entity.LuckyDucksTrackedData;
import me.ultrusmods.luckyducks.data.RubberDuckRegistry;
import me.ultrusmods.luckyducks.entity.RubberDuckEntity;
import me.ultrusmods.luckyducks.entity.RubberDuckType;
import me.ultrusmods.luckyducks.item.RubberDuckItem;
import me.ultrusmods.luckyducks.trade.LuckyDuckTrades;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuckyDucksMod implements ModInitializer {
	public static final String MOD_ID = "luckyducks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final EntityType<RubberDuckEntity> RUBBER_DUCK = Registry.register(
			Registries.ENTITY_TYPE,
			id("rubber_duck"),
			QuiltEntityTypeBuilder.create(SpawnGroup.CREATURE, RubberDuckEntity::new).setDimensions(EntityDimensions.fixed(0.65f, 0.65f)).build()
	);
	public static final Item RUBBER_DUCK_SPAWN_EGG = Registry.register(Registries.ITEM, id("rubber_duck_spawn_egg"), new SpawnEggItem(RUBBER_DUCK, 0xFFF167, 0xD37400, (new Item.Settings())));
	public static final Item RUBBER_DUCK_ITEM = Registry.register(Registries.ITEM, id("rubber_duck"), new RubberDuckItem(new QuiltItemSettings().equipmentSlot(EquipmentSlot.HEAD)));
	public static SoundEvent RUBBER_DUCK_SQUEAK = Registry.register(Registries.SOUND_EVENT, id("rubber_duck_squeak"), SoundEvent.createVariableRangeEvent(id("rubber_duck_squeak")));


	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		RubberDuckType.init();
		RubberDuckRegistry.init();
		DucksLoader.loadDucks();
		LuckyDucksTrackedData.init();
		FabricDefaultAttributeRegistry.register(RUBBER_DUCK, RubberDuckEntity.createRubberDuckAttributes());
		Registry.register(Registries.ITEM_GROUP, id("ducks"),
			FabricItemGroup.builder()
				.name(Text.translatable("itemGroup.luckyducks.ducks"))
				.icon(() -> new ItemStack(RUBBER_DUCK_ITEM))
				.entries((displayParameters, stackCollector) -> {
					for (RubberDuckType type : RubberDuckRegistry.RUBBER_DUCK_TYPES) {
						stackCollector.addStack(type.createStack());
					}
				})
				.build()
		);
		DispenserBlock.registerBehavior(RUBBER_DUCK_ITEM, new RubberDuckDispenserBehavior());
		LuckyDuckTrades.init();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
