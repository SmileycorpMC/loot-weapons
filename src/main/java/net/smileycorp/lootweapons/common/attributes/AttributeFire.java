package net.smileycorp.lootweapons.common.attributes;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.smileycorp.lootweapons.common.LootWeaponsContent;

public class AttributeFire implements ElementalWeaponAttribute {
    
    @Override
    public DamageSource getDamageSource(Entity attacker, LivingEntity target) {
        return new DamageSource(attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(LootWeaponsContent.FIRE_DAMAGE), attacker);
    }
    
}
