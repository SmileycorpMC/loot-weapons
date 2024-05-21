package net.smileycorp.lootweapons.common.attributes;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public interface ElementalWeaponAttribute {
    
    DamageSource getDamageSource(Entity attacker, LivingEntity target);
    
}
