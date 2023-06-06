package me.ultrusmods.luckyducks.data;

import com.mojang.serialization.Codec;
import net.minecraft.util.Rarity;
import net.minecraft.util.StringIdentifiable;

public enum DataRarity implements StringIdentifiable {

	COMMON(Rarity.COMMON, "common"),
	UNCOMMON(Rarity.UNCOMMON, "uncommon"),
	RARE(Rarity.RARE, "rare"),
	EPIC(Rarity.EPIC, "epic");
	public static final Codec<DataRarity> CODEC = StringIdentifiable.createCodec(DataRarity::values);

	private final Rarity rarity;
	private final String name;
	private DataRarity(Rarity rarity, String name) {
		this.rarity = rarity;
		this.name = name;
	}

	@Override
	public String asString() {
		return this.name;
	}

	public Rarity getRarity() {
		return this.rarity;
	}
}
