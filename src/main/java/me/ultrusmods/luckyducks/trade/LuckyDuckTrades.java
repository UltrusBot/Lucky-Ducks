package me.ultrusmods.luckyducks.trade;

import me.ultrusmods.luckyducks.data.RubberDuckType;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;

public class LuckyDuckTrades {
	public static void init() {
		TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {
			RubberDuckType.DUCK_SETS.get(RubberDuckType.COLOR_SET).forEach(rubberDuckType -> {
				factories.add(new SellDuckFactory(rubberDuckType, 15, 1, 1, 1));
			});
			RubberDuckType.DUCK_SETS.get(RubberDuckType.PRIDE_SET).forEach(rubberDuckType -> {
				factories.add(new SellDuckFactory(rubberDuckType, 15, 1, 1, 1));
			});
		});
	}
}
