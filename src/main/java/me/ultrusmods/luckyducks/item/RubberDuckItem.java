package me.ultrusmods.luckyducks.item;

import me.ultrusmods.luckyducks.LuckyDucksMod;
import me.ultrusmods.luckyducks.data.RubberDuckRegistry;
import me.ultrusmods.luckyducks.entity.RubberDuckEntity;
import me.ultrusmods.luckyducks.entity.RubberDuckType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Equippable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RubberDuckItem extends Item implements Equippable {
	public RubberDuckItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (!(context.getWorld() instanceof ServerWorld)) {
			return ActionResult.SUCCESS;
		}
		RubberDuckEntity duckEntity = createFromStack(context.getStack(), context.getWorld());
		Vec3d pos = context.getHitPos();
		duckEntity.lookAtEntity(context.getPlayer(), 360, 360);
		if (context.getPlayer() != null && context.getPlayer().isSneaking()) {
			duckEntity.toggleStill(); // Makes the duck not move.
		}
		duckEntity.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
		context.getWorld().spawnEntity(duckEntity);
		context.getStack().decrement(1);
		return ActionResult.SUCCESS;
	}


	public static RubberDuckEntity createFromStack(ItemStack stack, World world) {
		NbtCompound nbt = stack.getSubNbt("duckEntity");
		var duckEntity = LuckyDucksMod.RUBBER_DUCK.create(world);
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
		return duckEntity;
	}

	public static ItemStack stackFromType(RubberDuckType duckType) {
		ItemStack rubberDuckItem = LuckyDucksMod.RUBBER_DUCK_ITEM.getDefaultStack();
		rubberDuckItem.getOrCreateSubNbt("duckEntity").putString("type", duckType.id().toString());
		return rubberDuckItem;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		NbtCompound nbt = stack.getSubNbt("duckEntity");
		if (nbt != null) {
			String typeString = nbt.getString("type");
			if (typeString != "") {
				RubberDuckType type = RubberDuckRegistry.RUBBER_DUCK_TYPES.get(new Identifier(typeString));
				if (type == null) type = RubberDuckType.DEFAULT;
				tooltip.add(Text.translatable("text.luckyducks.duck_type").append(": ").append(Text.translatable(type.getTranslationKey())).formatted(Formatting.GRAY));

			}
		}
		super.appendTooltip(stack, world, tooltip, context);
	}

	@Override
	public EquipmentSlot getPreferredSlot() {
		return EquipmentSlot.HEAD;
	}
}
