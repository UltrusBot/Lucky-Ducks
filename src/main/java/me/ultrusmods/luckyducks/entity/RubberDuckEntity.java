package me.ultrusmods.luckyducks.entity;

import me.ultrusmods.luckyducks.LuckyDucksMod;
import me.ultrusmods.luckyducks.data.RubberDuckRegistry;
import me.ultrusmods.luckyducks.data.RubberDuckType;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.HashMap;

public class RubberDuckEntity extends PathAwareEntity {
	private static final TrackedData<RubberDuckType> TYPE = DataTracker.registerData(RubberDuckEntity.class, LuckyDucksTrackedData.RUBBER_DUCK_TYPE);
	private static final TrackedData<Boolean> IS_STILL = DataTracker.registerData(RubberDuckEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final HashMap<DyeColor, RubberDuckType> DYED_DUCK_COLORS = new HashMap<>();
	public RubberDuckEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new StillGoal(this));
		this.goalSelector.add(2, new WanderAroundGoal(this, 1, 10));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(TYPE, RubberDuckType.DEFAULT);
		this.dataTracker.startTracking(IS_STILL, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putString("duck_type", RubberDuckRegistry.RUBBER_DUCK_TYPES.getId(this.getDuckType()).toString());
		nbt.putBoolean("still", this.isStill());
	}

	public boolean isStill() {
		return this.dataTracker.get(IS_STILL);
	}

	public void toggleStill() {
		this.dataTracker.set(IS_STILL, !this.isStill());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		RubberDuckType duckType = RubberDuckRegistry.RUBBER_DUCK_TYPES.get(Identifier.tryParse(nbt.getString("duck_type")));
		if (duckType != null) {
			this.setType(duckType);
		}
		boolean still = nbt.getBoolean("still");
		this.dataTracker.set(IS_STILL, still);
	}

	public static DefaultAttributeContainer.Builder createRubberDuckAttributes() {
		return MobEntity.createAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return LuckyDucksMod.RUBBER_DUCK_SQUEAK;
	}

	@Override
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		return (world.getFluidState(pos).isIn(FluidTags.WATER) || world.getFluidState(pos.down()).isIn(FluidTags.WATER) )? 20.0F : super.getPathfindingFavor(pos, world);
	}

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (!player.canModifyBlocks()) return ActionResult.PASS;
		if (player.isSneaking()) {
			ItemStack rubberDuckItem = LuckyDucksMod.RUBBER_DUCK_ITEM.getDefaultStack();
			rubberDuckItem.getOrCreateSubNbt("duckEntity").putString("type", RubberDuckRegistry.RUBBER_DUCK_TYPES.getId(getDuckType()).toString());
			if (this.hasCustomName()) {
				rubberDuckItem.setCustomName(this.getCustomName());
			}
			this.dropStack(rubberDuckItem);
			this.discard();
			return ActionResult.SUCCESS;
		}
		if (itemStack.getItem() instanceof DyeItem dyeItem && DYED_DUCK_COLORS.containsKey(dyeItem.getColor())) {
			RubberDuckType type = DYED_DUCK_COLORS.get(dyeItem.getColor());
			itemStack.decrement(1);
			this.setType(type);
			this.getWorld().playSoundFromEntity(player, this, SoundEvents.ITEM_DYE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
			return ActionResult.success(player.getWorld().isClient);
		}
		if (itemStack.isEmpty()) {
			this.toggleStill();
			this.lookAtEntity(player, 360, 360);
			this.navigation.stop();
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}

	@Override
	public boolean isPushable() {
		return !this.isStill();
	}

	@Override
	public void onDeath(DamageSource source) {
		ItemStack rubberDuckItem = LuckyDucksMod.RUBBER_DUCK_ITEM.getDefaultStack();
		rubberDuckItem.getOrCreateSubNbt("duckEntity").putString("type", RubberDuckRegistry.RUBBER_DUCK_TYPES.getId(getDuckType()).toString());
		if (this.hasCustomName()) {
			rubberDuckItem.setCustomName(this.getCustomName());
		}
		this.dropStack(rubberDuckItem);
		super.onDeath(source);
	}

	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		setType(this.random.nextBoolean() ? RubberDuckType.DEFAULT : RubberDuckType.DEFAULT2);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public void setType(RubberDuckType type) {
		this.dataTracker.set(TYPE, type);
	}


	@Override
	public boolean cannotDespawn() {
		return true;
	}

	public RubberDuckType getDuckType() {
		return this.dataTracker.get(TYPE);
	}

	static {
		DYED_DUCK_COLORS.put(DyeColor.WHITE, RubberDuckType.WHITE);
		DYED_DUCK_COLORS.put(DyeColor.ORANGE, RubberDuckType.ORANGE);
		DYED_DUCK_COLORS.put(DyeColor.YELLOW, RubberDuckType.DEFAULT);
		DYED_DUCK_COLORS.put(DyeColor.MAGENTA, RubberDuckType.MAGENTA);
		DYED_DUCK_COLORS.put(DyeColor.LIGHT_BLUE, RubberDuckType.LIGHT_BLUE);
		DYED_DUCK_COLORS.put(DyeColor.LIME, RubberDuckType.LIME);
		DYED_DUCK_COLORS.put(DyeColor.PINK, RubberDuckType.PINK);
		DYED_DUCK_COLORS.put(DyeColor.GRAY, RubberDuckType.GRAY);
		DYED_DUCK_COLORS.put(DyeColor.LIGHT_GRAY, RubberDuckType.LIGHT_GRAY);
		DYED_DUCK_COLORS.put(DyeColor.CYAN, RubberDuckType.CYAN);
		DYED_DUCK_COLORS.put(DyeColor.PURPLE, RubberDuckType.PURPLE);
		DYED_DUCK_COLORS.put(DyeColor.BLUE, RubberDuckType.BLUE);
		DYED_DUCK_COLORS.put(DyeColor.BROWN, RubberDuckType.BROWN);
		DYED_DUCK_COLORS.put(DyeColor.GREEN, RubberDuckType.GREEN);
		DYED_DUCK_COLORS.put(DyeColor.RED, RubberDuckType.RED);
		DYED_DUCK_COLORS.put(DyeColor.BLACK, RubberDuckType.BLACK);
	}

	private static class StillGoal extends Goal {
		private final RubberDuckEntity duck;

		public StillGoal(RubberDuckEntity duck) {
			this.duck = duck;
			this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
		}

		@Override
		public boolean canStart() {
			return this.duck.isStill() && this.duck.isOnGround();
		}

		@Override
		public boolean shouldContinue() {
			return this.duck.isStill();
		}

		@Override
		public void start() {
			this.duck.getNavigation().stop();
		}
	}
}
