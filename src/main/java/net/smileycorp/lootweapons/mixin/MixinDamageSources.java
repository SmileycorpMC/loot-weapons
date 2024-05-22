package net.smileycorp.lootweapons.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.smileycorp.lootweapons.common.attributes.ElementalWeaponAttribute;
import net.smileycorp.lootweapons.common.item.LootItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageSources.class)
public class MixinDamageSources {
    
    @Inject(at = @At("HEAD"), method = "mobAttack", cancellable = true)
    public void mobAttack(LivingEntity entity, CallbackInfoReturnable<DamageSource> callback) {
        ItemStack stack = entity.getMainHandItem();
        if (stack.getItem() instanceof LootItem) {
            ElementalWeaponAttribute attribute = ((LootItem) stack.getItem()).getLootData(stack).getAttribute();
            if (attribute != null) callback.setReturnValue(attribute.getDamageSource(entity));
        }
    }
    
}
