package me.ultrusmods.luckyducks.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import dev.lukebemish.defaultresources.api.ResourceProvider;
import me.ultrusmods.luckyducks.LuckyDucksMod;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.util.ArrayList;

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
					Registry.register(RubberDuckRegistry.RUBBER_DUCK_TYPES, duck.id(), duck);
					if (!RubberDuckType.DUCK_SETS.containsKey(duck.set())) {
						RubberDuckType.DUCK_SETS.put(duck.set(), new ArrayList<>());
					}
					RubberDuckType.DUCK_SETS.get(duck.set()).add(duck);
				}
			} catch (Exception e) {
				LuckyDucksMod.LOGGER.error("Failed to load duck: " + identifier, e);
			}
		}
	}
}
