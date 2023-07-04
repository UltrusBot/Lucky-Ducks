package me.ultrusmods.luckyducks.trade;

import me.ultrusmods.luckyducks.data.RubberDuckType;
import me.ultrusmods.luckyducks.item.RubberDuckItem;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

public class SellDuckFactory implements TradeOffers.Factory{

	private final RubberDuckType duckType;
	private final int price;
	private final int maxUses;
	private final int experience;
	private final float multiplier;

	public SellDuckFactory(RubberDuckType duckType, int price, int maxUses, int experience) {
		this(duckType, price, maxUses, experience, 0.05F);
	}

	public SellDuckFactory(RubberDuckType duckType, int price, int maxUses, int experience, float multiplier) {
		this.duckType = duckType;
		this.price = price;
		this.maxUses = maxUses;
		this.experience = experience;
		this.multiplier = multiplier;
	}

	@Override
	public TradeOffer create(Entity entity, RandomGenerator random) {
		return new TradeOffer(
				new ItemStack(Items.EMERALD, this.price), RubberDuckItem.stackFromType(duckType), this.maxUses, this.experience, this.multiplier
		);
	}
}
