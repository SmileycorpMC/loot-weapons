package net.smileycorp.lootweapons.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.smileycorp.lootweapons.common.Constants;
import net.smileycorp.lootweapons.common.LootWeapons;
import net.smileycorp.lootweapons.common.LootWeaponsLogger;
import net.smileycorp.lootweapons.common.attributes.ElementalWeaponAttribute;
import net.smileycorp.lootweapons.common.data.LootData;

import java.awt.*;
import java.util.Locale;

public interface LootItem {
    
    RandomSource rand = RandomSource.createNewThreadLocalInstance();
    
    default CompoundTag generateNBT(int base, LootContext context) {
        return generateNBT(getLuckFromContext(base, context));
    }
    
    default CompoundTag generateNBT(int luck) {
        double rarity = getRarityValue(luck);
        CompoundTag tag = generateNBT(getRarity(rarity), luck, rarity);
        tag.putDouble("RarityValue", rarity);
        return tag;
    }
    
    CompoundTag generateNBT(Rarity randomRarity, int luck, double rarityValue);
    
    //feels like a pretty good distribution, mostly common and uncommon for luck < 2, starts distributing rares, legendaries and mythicals later
    //allows for a general distribution with values weighted to the current tier, while allowing commons to always generate, but weighting away from them
    //may have to tweak the distribution slightly so that generating above the upper bound is preferred instead of below
    //may need to tweak the rarity values or the luck multiplier with testing
    default double getRarityValue(int luck) {
        double rarity = Mth.clamp(rand.nextInt(2 * luck + 1) - (rand.nextInt(luck + 3)) * rand.nextGaussian(), 0, 20);;
        LootWeaponsLogger.logInfo("Generated rarity " + rarity + " for luck value " + luck);
        return rarity;
    }
    
    default Rarity getRarity(double value) {
        if (value > 16) return Constants.MYTHICAL;
        if (value > 11) return Constants.LEGENDARY;
        if (value > 7) return Rarity.EPIC;
        if (value > 4) return Rarity.RARE;
        if (value > 1) return Rarity.UNCOMMON;
        return Rarity.COMMON;
    }
    
    //multiply luck and looting modifiers by the base to hopefully make a luck value in a good range
    default int getLuckFromContext(int base, LootContext context) {
        return (int) ((double)base * ((context.getLootingModifier() + 1) * 0.5f) * context.getLuck());
    }
    
    //multiply luck modifier by the base to hopefully make a luck value in a good range
    default int getLuckFromEntity(int base, LivingEntity entity) {
        return (int) ((double)base * 0.5f * entity.getAttribute(Attributes.LUCK).getValue());
    }
    
    default int getRandomMaterialColour(double rarityValue) {
        //pick a random hue
        float hue = rand.nextInt(360) * 0.00277777f;
        //higher rarity items should ideally look more saturated than lower ones to make them look more unique and less dull
        //clamp rarity to not overapply or underapply saturation
        int r = (int) Mth.clamp(rarityValue, 1, 25);
        //should proportionally weight to the higher bounds
        float saturation = (rand.nextInt(r) +  rand.nextInt(r) + rand.nextInt(r) + rand.nextInt(r)) * 0.01f;
        //random brightness, don't want it below 40 so the texture isn't too dark
        float brightness = rand.nextInt(rand.nextInt(60) + 40) * 0.01f;
        return Color.HSBtoRGB(hue, saturation, brightness);
    }
    default float calculateAttributeMultiplier(double rarityValue) {
        return  0.75f + ((rand.nextInt((int) rarityValue + 1))) * 0.1f * rand.nextFloat();
    }
    
    
    //random distribution to calculate if a weapon should have an elemental attribute
    //weights rarity as well as initial luck, should usually favour luck unless the rolled weapon has a high rarity value
    default boolean shouldHaveElementalAttribute(int luck, double rarityValue) {
        return (rand.nextInt(luck + 3) * rand.nextInt((int) rarityValue + 1)) * rand.nextGaussian() > 5;
    }
    
    //generate a descriptor used in the item toolip following the format rarity element weapon-type
    //e.g. Common Sword, Legendary Blazing Axe, Epic Icy Spear, Common Poisonous Knife, Mythical Chestplate
    default MutableComponent getDescription(ItemStack stack, Component name) {
        MutableComponent description = Component.translatable("text.lootweapons.rarity." + getRarity(stack).name()
                .toLowerCase(Locale.US)).append(" ");
        ElementalWeaponAttribute attribute = getElementalAttribute(stack);
        if (attribute != null) description.append(attribute.getName()).append(" ");
        return description.append(name);
    }
    
    default Rarity getRarity(ItemStack stack) {
        return getLootData(stack).getRarity();
    }
    
    default ElementalWeaponAttribute getElementalAttribute(ItemStack stack) {
        return getLootData(stack).getAttribute();
    }
    
    default LootData getLootData(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains("LootData") ? LootData.fromTag(tag.getCompound("LootData")) : LootData.EMPTY;
    }
    
    default boolean hasLootData(ItemStack stack) {
        return !getLootData(stack).isEmpty();
    }
    
}
