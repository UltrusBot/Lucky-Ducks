package me.ultrusmods.luckyducks.entity;

import me.ultrusmods.luckyducks.LuckyDucksMod;
import me.ultrusmods.luckyducks.data.RubberDuckRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public record RubberDuckType(Identifier texture, Identifier id) {
	public static final RubberDuckType DEFAULT = createDuck("default", "textures/entity/rubber_duck/default_duck.png");
	public static final RubberDuckType DEFAULT2 = createDuck("default2", "textures/entity/rubber_duck/default_duck_2.png");
	public static final RubberDuckType WHITE = createDuck("white", "textures/entity/rubber_duck/white_duck.png");
	public static final RubberDuckType ORANGE = createDuck("orange", "textures/entity/rubber_duck/orange_duck.png");
	public static final RubberDuckType MAGENTA = createDuck("magenta", "textures/entity/rubber_duck/magenta_duck.png");
	public static final RubberDuckType LIGHT_BLUE = createDuck("light_blue", "textures/entity/rubber_duck/light_blue_duck.png");
	public static final RubberDuckType LIME = createDuck("lime", "textures/entity/rubber_duck/lime_duck.png");
	public static final RubberDuckType PINK = createDuck("pink", "textures/entity/rubber_duck/pink_duck.png");
	public static final RubberDuckType GRAY = createDuck("gray", "textures/entity/rubber_duck/gray_duck.png");
	public static final RubberDuckType LIGHT_GRAY = createDuck("light_gray", "textures/entity/rubber_duck/light_gray_duck.png");
	public static final RubberDuckType CYAN = createDuck("cyan", "textures/entity/rubber_duck/cyan_duck.png");
	public static final RubberDuckType PURPLE = createDuck("purple", "textures/entity/rubber_duck/purple_duck.png");
	public static final RubberDuckType BLUE = createDuck("blue", "textures/entity/rubber_duck/blue_duck.png");
	public static final RubberDuckType BROWN = createDuck("brown", "textures/entity/rubber_duck/brown_duck.png");
	public static final RubberDuckType GREEN = createDuck("green", "textures/entity/rubber_duck/green_duck.png");
	public static final RubberDuckType RED = createDuck("red", "textures/entity/rubber_duck/red_duck.png");
	public static final RubberDuckType BLACK = createDuck("black", "textures/entity/rubber_duck/black_duck.png");
	public static void init() {

	}
	public static RubberDuckType createDuck(Identifier id, Identifier texture) {
		return Registry.register(RubberDuckRegistry.RUBBER_DUCK_TYPES, id, new RubberDuckType(texture, id));
	}
	private static RubberDuckType createDuck(String name, String texture) {
		return createDuck(LuckyDucksMod.id(name), LuckyDucksMod.id(texture));
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
