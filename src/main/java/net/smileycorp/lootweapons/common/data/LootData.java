package net.smileycorp.lootweapons.common.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.smileycorp.lootweapons.common.Constants;
import net.smileycorp.lootweapons.common.attributes.ElementalWeaponAttribute;
import net.smileycorp.lootweapons.common.attributes.ElementalWeaponAttributes;

import java.util.Locale;

public class LootData {
    
    public static final LootData EMPTY = new LootData(null, new byte[0], new int[0], new float[0], null);
    private final Rarity rarity;
    private final byte[] parts;
    private final int[] colours;
    private final float[] stats;
    
    private final ElementalWeaponAttribute attribute;
    
    private LootData(Rarity rarity, byte[] parts, int[] colours, float[] stats, ElementalWeaponAttribute attribute) {
        this.rarity = rarity;
        this.parts = parts;
        this.colours = colours;
        this.stats = stats;
        this.attribute = attribute;
    }
    
    public Rarity getRarity() {
        return rarity == null ? Rarity.COMMON : rarity;
    }
    
    public byte[] getParts() {
        return parts;
    }
    
    public int[] getColours() {
        return colours;
    }
    
    public float[] getStats() {
        return stats;
    }
    
    public ElementalWeaponAttribute getAttribute() {
        return attribute;
    }
    
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        if (rarity != null) tag.putString("rarity", rarity.name().toLowerCase(Locale.US));
        if (parts.length > 0) tag.putByteArray("parts", parts);
        if (colours.length > 0) tag.putIntArray("colours", colours);
        if (stats.length > 0) {
            ListTag list = new ListTag();
            for (float stat : stats) list.add(FloatTag.valueOf(stat));
            tag.put("stats", list);
        }
        if (attribute != null) {
            ResourceLocation name = attribute.getRegistryName();
            if (name != null) tag.putString("attribute", name.toString());
        }
        return tag;
    }
    
    public static LootData fromTag(CompoundTag tag) {
        if (tag == null) return LootData.EMPTY;
        Builder builder = new Builder(tag.contains("rarity") ? Constants.getRarity(tag.getString("rarity")) : Rarity.COMMON);
        if (tag.contains("parts")) builder.parts(tag.getByteArray("parts"));
        if (tag.contains("colours")) builder.colours(tag.getIntArray("colours"));
        if (tag.contains("stats")) {
            ListTag list = tag.getList("stats", Tag.TAG_FLOAT);
            float[] stats = new float[list.size()];
            for (int i = 0; i < list.size(); i++) stats[i] = list.getFloat(i);
            builder.stats(stats);
        }
        if (tag.contains("attribute")) builder.attribute(ElementalWeaponAttributes.get(new ResourceLocation(tag.getString("attribute"))));
        return builder.build();
    }
    
    public boolean isEmpty() {
        if (this == EMPTY) return true;
        return parts.length == 0 && colours.length == 0 && stats.length == 0;
    }
    
    public static class Builder {
    
        private final Rarity rarity;
        private byte[] parts = new byte[0];
        private int[] colours = new int[0];
        private float[] stats = new float[0];
    
        private ElementalWeaponAttribute attribute = null;
        
        public Builder(Rarity rarity) {
            this.rarity = rarity;
        }
    
        public Builder parts(byte... parts) {
            this.parts = parts;
            return this;
        }
        
        public Builder colours(int... colours) {
            this.colours = colours;
            return this;
        }
    
        public Builder stats(float... stats) {
            this.stats = stats;
            return this;
        }
    
        public Builder attribute(ElementalWeaponAttribute attribute) {
            this.attribute = attribute;
            return this;
        }
        
        public LootData build() {
            return new LootData(rarity, parts, colours, stats, attribute);
        }
        
    }
    
}
