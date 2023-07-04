package me.ultrusmods.luckyducks.trinket;

import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import me.ultrusmods.luckyducks.LuckyDucksMod;
import net.minecraft.entity.player.PlayerEntity;

public class TrinketsCompat {

	public static void registerRenderers() {
		TrinketRendererRegistry.registerRenderer(LuckyDucksMod.RUBBER_DUCK_ITEM, new LuckyDuckTrinketRenderer());
	}

	public static boolean hasDuckTrinket(PlayerEntity playerEntity) {
		return TrinketsApi.getTrinketComponent(playerEntity).map(trinketComponent -> trinketComponent.isEquipped(LuckyDucksMod.RUBBER_DUCK_ITEM)).orElse(false);
	}


}
