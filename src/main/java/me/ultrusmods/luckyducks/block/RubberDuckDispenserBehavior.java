package me.ultrusmods.luckyducks.block;

import me.ultrusmods.luckyducks.entity.RubberDuckEntity;
import me.ultrusmods.luckyducks.item.RubberDuckItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;

public class RubberDuckDispenserBehavior extends ItemDispenserBehavior {
	@Override
	protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
		try {
			RubberDuckEntity duckEntity = RubberDuckItem.createFromStack(stack, pointer.getWorld());
			BlockPos pos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
			duckEntity.refreshPositionAndAngles(pos.getX(), pos.getY()+1, pos.getZ(), 0.0F, 0.0F);
			pointer.getWorld().spawnEntity(duckEntity);
			stack.decrement(1);
			pointer.getWorld().emitGameEvent(null, GameEvent.ENTITY_PLACE, pointer.getPos());
			return stack;
		} catch (Exception var6) {
			LOGGER.error("Error while dispensing rubber duck from dispenser at {}", pointer.getPos(), var6);
			return ItemStack.EMPTY;
		}
	}
}
