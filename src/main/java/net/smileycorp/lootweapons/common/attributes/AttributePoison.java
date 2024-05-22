package net.smileycorp.lootweapons.common.attributes;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.smileycorp.lootweapons.common.LootWeaponsContent;

public class AttributePoison implements ElementalWeaponAttribute {
    
    @Override
    public DamageSource getDamageSource(Entity attacker) {
        return new DamageSource(attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(LootWeaponsContent.POISON_DAMAGE), attacker);
    }
    
}
