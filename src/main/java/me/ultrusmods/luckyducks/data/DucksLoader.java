package me.ultrusmods.luckyducks.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import dev.lukebemish.defaultresources.api.ResourceProvider;
import me.ultrusmods.luckyducks.LuckyDucksMod;
import me.ultrusmods.luckyducks.entity.RubberDuckType;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;

public class DucksLoader {
	private static final Gson GSON = new Gson();
	public static void loadDucks() {
		ResourceProvider.forceInitialization();
		var identifiers = ResourceProvider.instance().getResources(LuckyDucksMod.MOD_ID, "ducks", predicate -> true);
		for (Identifier identifier : identifiers) {
			try (var resourceStream = ResourceProvider.instance().getResourceStreams(LuckyDucksMod.MOD_ID, identifier)) {
				var optional = resourceStream.findFirst();
				if (optional.isPresent()) {
					var stream = optional.get();
					JsonObject jsonObject = GSON.fromJson(new InputStreamReader(stream), JsonObject.class);
					var duck = RubberDuckType.CODEC.decode(JsonOps.INSTANCE, jsonObject).getOrThrow(false, LuckyDucksMod.LOGGER::error).getFirst();
					Registry.register(RubberDuckRegistry.RUBBER_DUCK_TYPES, identifier, duck);
				}
			} catch (Exception e) {
				LuckyDucksMod.LOGGER.error("Failed to load duck: " + identifier, e);
			}
		}
	}
}
