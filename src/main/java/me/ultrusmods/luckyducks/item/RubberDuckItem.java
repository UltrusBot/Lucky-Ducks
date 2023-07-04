package me.ultrusmods.luckyducks.item;

import me.ultrusmods.luckyducks.LuckyDucksMod;
import me.ultrusmods.luckyducks.data.RubberDuckRegistry;
import me.ultrusmods.luckyducks.data.RubberDuckType;
import me.ultrusmods.luckyducks.entity.RubberDuckEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class RubberDuckItem extends Item implements Equippable {
	public RubberDuckItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (context.getWorld() instanceof ServerWorld serverWorld) {
			RubberDuckEntity duckEntity = createFromStack(context.getStack(), serverWorld, context.getBlockPos(), context.getPlayer(), context.getHitPos());
			if (duckEntity == null) {
				return ActionResult.FAIL;
			}
//			duckEntity.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), (MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F)), 0.0f);
			serverWorld.spawnEntity(duckEntity);
			if (context.getPlayer() != null) {
				if (context.getPlayer().isSneaking()) {
					duckEntity.toggleStill(); // Makes the duck not move.
				}
			}
			duckEntity.emitGameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
		}
		context.getStack().decrement(1);
		return ActionResult.SUCCESS;
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		NbtCompound nbt = stack.getSubNbt("duckEntity");
		if (nbt != null) {
			String typeString = nbt.getString("type");
			if (!Objects.equals(typeString, "")) {
				RubberDuckType type = RubberDuckRegistry.RUBBER_DUCK_TYPES.get(new Identifier(typeString));
				if (type == null) {
					type = RubberDuckType.DEFAULT;
				}
				return type.rarity().getRarity();
			}
		}
		return Rarity.COMMON;
	}

	public static RubberDuckEntity createFromStack(ItemStack stack, ServerWorld world, BlockPos blockPos, @Nullable PlayerEntity playerEntity, Vec3d pos) {
		NbtCompound nbt = stack.getOrCreateSubNbt("duckEntity");
		var duckEntity = LuckyDucksMod.RUBBER_DUCK.create(world);
//		Consumer<RubberDuckEntity> config = EntityType.createDefaultStackSpawnConfig(world, stack, playerEntity);
//		RubberDuckEntity duckEntity = LuckyDucksMod.RUBBER_DUCK.create(world, nbt, config, blockPos, SpawnReason.TRIGGERED, true, true);
		if (duckEntity == null) {
			return null;
		}
		if (playerEntity != null) {
			// TODO: Make this actually work, doesn't update on the client.
			duckEntity.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), (MathHelper.wrapDegrees(playerEntity.getYaw() - 180.0F)), 0.0f);
		}

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

	public static ItemStack stackFromType(Identifier duckType) {
		ItemStack rubberDuckItem = LuckyDucksMod.RUBBER_DUCK_ITEM.getDefaultStack();
		rubberDuckItem.getOrCreateSubNbt("duckEntity").putString("type", duckType.toString());
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
