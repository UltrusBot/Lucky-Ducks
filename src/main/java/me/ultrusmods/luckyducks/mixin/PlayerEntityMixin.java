package me.ultrusmods.luckyducks.mixin;

import me.ultrusmods.luckyducks.LuckyDucksMod;
import me.ultrusmods.luckyducks.trinket.TrinketsCompat;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

	@Shadow
	public abstract ItemStack getEquippedStack(EquipmentSlot slot);

	@Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
	public void addDuckSqueakSound$LuckyDucks(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
		if (getEquippedStack(EquipmentSlot.HEAD).isOf(LuckyDucksMod.RUBBER_DUCK_ITEM)) {
			cir.setReturnValue(LuckyDucksMod.RUBBER_DUCK_SQUEAK);
		} else if (LuckyDucksMod.TRINKETS_LOADED) {
			if (TrinketsCompat.hasDuckTrinket((PlayerEntity) (Object) this)) {
				cir.setReturnValue(LuckyDucksMod.RUBBER_DUCK_SQUEAK);
			}
		}
	}
}
