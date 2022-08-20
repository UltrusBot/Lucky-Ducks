package me.ultrusmods.luckyducks.trade;

import me.ultrusmods.luckyducks.entity.RubberDuckType;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;

public class LuckyDuckTrades {
	public static void init() {
		TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {
			factories.add(new SellDuckFactory(RubberDuckType.DEFAULT, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.DEFAULT2, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.WHITE, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.ORANGE, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.MAGENTA, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.LIGHT_BLUE, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.LIME, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.PINK, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.GRAY, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.LIGHT_GRAY, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.CYAN, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.PURPLE, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.BLUE, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.BROWN, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.GREEN, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.RED, 15, 1, 1, 1));
			factories.add(new SellDuckFactory(RubberDuckType.BLACK, 15, 1, 1, 1));
		});
	}
}
