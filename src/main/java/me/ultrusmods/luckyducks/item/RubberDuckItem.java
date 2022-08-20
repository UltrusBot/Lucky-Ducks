package me.ultrusmods.luckyducks.item;

import me.ultrusmods.luckyducks.LuckyDucksMod;
import me.ultrusmods.luckyducks.data.RubberDuckRegistry;
import me.ultrusmods.luckyducks.entity.RubberDuckType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Wearable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class RubberDuckItem extends Item implements Wearable {
	public RubberDuckItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (!(context.getWorld() instanceof ServerWorld)) {
			return ActionResult.SUCCESS;
		}
		NbtCompound nbt = context.getStack().getSubNbt("duckEntity");
		ItemStack stack = context.getStack();
		var duckEntity = LuckyDucksMod.RUBBER_DUCK.create(context.getWorld());
		if (nbt != null) {
			String typeString = nbt.getString("type");
			if (typeString != "") {
				RubberDuckType type = RubberDuckRegistry.RUBBER_DUCK_TYPES.get(new Identifier(typeString));
				if (type == null) {
					type = RubberDuckType.DEFAULT;
				}
				duckEntity.setType(type);
			}
		}
		if (stack.hasCustomName()) {
			duckEntity.setCustomName(stack.getName());
		}
		BlockPos pos = context.getBlockPos();
		duckEntity.refreshPositionAndAngles(pos.getX(), pos.getY()+1, pos.getZ(), 0.0F, 0.0F);
		context.getWorld().spawnEntity(duckEntity);
		return ActionResult.SUCCESS;

	}
}
