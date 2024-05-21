package net.smileycorp.lootweapons.common.attributes;

import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.smileycorp.lootweapons.common.Constants;

import java.util.Map;

public class ElementalWeaponAttributes {
    
    private static final Map<ResourceLocation, ElementalWeaponAttribute> ATTRIBUTE_MAP = Maps.newHashMap();
    
    public static final ElementalWeaponAttribute ELECTRIC = register(Constants.loc("electric"), new AttributeElectric());
    public static final ElementalWeaponAttribute FIRE = register(Constants.loc("fire"), new AttributeFire());
    public static final ElementalWeaponAttribute ICE = register(Constants.loc("ice"), new AttributeIce());
    public static final ElementalWeaponAttribute POISON = register(Constants.loc("poison"), new AttributePoison());
    
    public static ElementalWeaponAttribute register(ResourceLocation name, ElementalWeaponAttribute attribute) {
        ATTRIBUTE_MAP.put(name, attribute);
        return attribute;
    }
    
    public static ElementalWeaponAttribute get(ResourceLocation name) {
        return ATTRIBUTE_MAP.get(name);
    }
    
    public static ResourceLocation getName(ElementalWeaponAttribute attribute) {
        for (Map.Entry<ResourceLocation, ElementalWeaponAttribute> entry : ATTRIBUTE_MAP.entrySet())
            if (entry.getValue() == attribute) return entry.getKey();
        return null;
    }
    
}
