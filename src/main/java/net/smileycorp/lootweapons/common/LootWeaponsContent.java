package net.smileycorp.lootweapons.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class LootWeaponsContent {
    
    public static final ResourceKey<DamageType> FIRE_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Constants.loc("fire_weapon"));
    public static final ResourceKey<DamageType> ICE_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Constants.loc("ice_weapon"));
    public static final ResourceKey<DamageType> ELECTRIC_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Constants.loc("electric_weapon"));
    public static final ResourceKey<DamageType> POISON_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Constants.loc("poison_damage"));
    
}
