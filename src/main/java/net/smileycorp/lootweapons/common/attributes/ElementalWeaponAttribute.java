package net.smileycorp.lootweapons.common.attributes;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public interface ElementalWeaponAttribute {
    
    DamageSource getDamageSource(Entity attacker);
    
    default ResourceLocation getRegistryName() {
        return ElementalWeaponAttributes.getName(this);
    }
    
    default MutableComponent getName() {
        ResourceLocation loc = getRegistryName();
        return Component.translatable("text." + loc.getNamespace() +  ".attribute." + loc.getPath());
    }
    
}
