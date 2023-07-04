package me.ultrusmods.luckyducks.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.ultrusmods.luckyducks.LuckyDucksMod;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.*;

public record RubberDuckType(Identifier texture, Identifier id, DataRarity rarity, Optional<OverlayData> overlayData, Identifier set) {
	public static Map<Identifier, List<RubberDuckType>> DUCK_SETS = new HashMap<>();
	public static Codec<RubberDuckType> CODEC = RecordCodecBuilder.create(builder -> builder.group(
		Identifier.CODEC.fieldOf("texture").forGetter(RubberDuckType::texture),
		Identifier.CODEC.fieldOf("id").forGetter(RubberDuckType::id),
		DataRarity.CODEC.optionalFieldOf("rarity", DataRarity.COMMON).forGetter(RubberDuckType::rarity),
		OverlayData.CODEC.optionalFieldOf("overlay").forGetter(RubberDuckType::overlayData),
		Identifier.CODEC.fieldOf("set").forGetter(RubberDuckType::set)
	).apply(builder, RubberDuckType::new));
	public static final Identifier COLOR_SET = new Identifier(LuckyDucksMod.MOD_ID, "color");
	public static final Identifier PRIDE_SET = new Identifier(LuckyDucksMod.MOD_ID, "pride");
	public static final RubberDuckType DEFAULT = createCommonDuck("default", "textures/entity/rubber_duck/default_duck.png", COLOR_SET);
	public static final RubberDuckType DEFAULT2 = createCommonDuck("default2", "textures/entity/rubber_duck/default_duck_2.png", COLOR_SET);
	public static final RubberDuckType WHITE = createCommonDuck("white", "textures/entity/rubber_duck/white_duck.png", COLOR_SET);
	public static final RubberDuckType ORANGE = createCommonDuck("orange", "textures/entity/rubber_duck/orange_duck.png", COLOR_SET);
	public static final RubberDuckType MAGENTA = createCommonDuck("magenta", "textures/entity/rubber_duck/magenta_duck.png", COLOR_SET);
	public static final RubberDuckType LIGHT_BLUE = createCommonDuck("light_blue", "textures/entity/rubber_duck/light_blue_duck.png", COLOR_SET);
	public static final RubberDuckType LIME = createCommonDuck("lime", "textures/entity/rubber_duck/lime_duck.png", COLOR_SET);
	public static final RubberDuckType PINK = createCommonDuck("pink", "textures/entity/rubber_duck/pink_duck.png", COLOR_SET);
	public static final RubberDuckType GRAY = createCommonDuck("gray", "textures/entity/rubber_duck/gray_duck.png", COLOR_SET);
	public static final RubberDuckType LIGHT_GRAY = createCommonDuck("light_gray", "textures/entity/rubber_duck/light_gray_duck.png", COLOR_SET);
	public static final RubberDuckType CYAN = createCommonDuck("cyan", "textures/entity/rubber_duck/cyan_duck.png", COLOR_SET);
	public static final RubberDuckType PURPLE = createCommonDuck("purple", "textures/entity/rubber_duck/purple_duck.png", COLOR_SET);
	public static final RubberDuckType BLUE = createCommonDuck("blue", "textures/entity/rubber_duck/blue_duck.png", COLOR_SET);
	public static final RubberDuckType BROWN = createCommonDuck("brown", "textures/entity/rubber_duck/brown_duck.png", COLOR_SET);
	public static final RubberDuckType GREEN = createCommonDuck("green", "textures/entity/rubber_duck/green_duck.png", COLOR_SET);
	public static final RubberDuckType RED = createCommonDuck("red", "textures/entity/rubber_duck/red_duck.png", COLOR_SET);
	public static final RubberDuckType BLACK = createCommonDuck("black", "textures/entity/rubber_duck/black_duck.png", COLOR_SET);
	public static final RubberDuckType PRIDE = createCommonDuck("pride", "textures/entity/rubber_duck/pride_duck.png", PRIDE_SET);
	public static final RubberDuckType ACE = createCommonDuck("ace", "textures/entity/rubber_duck/ace_duck.png", PRIDE_SET);
	public static final RubberDuckType AGENDER = createCommonDuck("agender", "textures/entity/rubber_duck/agender_duck.png", PRIDE_SET);
	public static final RubberDuckType ARO = createCommonDuck("aro", "textures/entity/rubber_duck/aro_duck.png", PRIDE_SET);
	public static final RubberDuckType BI = createCommonDuck("bi", "textures/entity/rubber_duck/bi_duck.png", PRIDE_SET);
	public static final RubberDuckType GENDERFLUID = createCommonDuck("genderfluid", "textures/entity/rubber_duck/genderfluid_duck.png", PRIDE_SET);
	public static final RubberDuckType LESBIAN = createCommonDuck("lesbian", "textures/entity/rubber_duck/lesbian_duck.png", PRIDE_SET);
	public static final RubberDuckType PAN = createCommonDuck("pan", "textures/entity/rubber_duck/pan_duck.png", PRIDE_SET);
	public static final RubberDuckType TRANS = createCommonDuck("trans", "textures/entity/rubber_duck/trans_duck.png", PRIDE_SET);

	public static void init() {

	}
	public static RubberDuckType createDuck(Identifier id, Identifier texture, DataRarity rarity, Identifier set) {
		RubberDuckType duckType = Registry.register(RubberDuckRegistry.RUBBER_DUCK_TYPES, id, new RubberDuckType(texture, id, rarity, Optional.empty(), set));
		if (!DUCK_SETS.containsKey(set)) {
			DUCK_SETS.put(set, new ArrayList<>());
		}
		DUCK_SETS.get(set).add(duckType);
		return duckType;
	}
	private static RubberDuckType createCommonDuck(String name, String texture, Identifier set) {
		return createDuck(LuckyDucksMod.id(name), LuckyDucksMod.id(texture), DataRarity.COMMON, set);
	}

	public String getTranslationKey() {
		return "rubberducktype.%s.%s".formatted(this.id.getNamespace(), this.id.getPath());
	}

	public ItemStack createStack() {
		ItemStack itemStack = LuckyDucksMod.RUBBER_DUCK_ITEM.getDefaultStack();
		itemStack.getOrCreateSubNbt("duckEntity").putString("type", RubberDuckRegistry.RUBBER_DUCK_TYPES.getId(this).toString());
		return itemStack;
	}
}
