package net.smileycorp.lootweapons.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.smileycorp.lootweapons.common.Constants;
import net.smileycorp.lootweapons.common.attributes.ElementalWeaponAttribute;
import net.smileycorp.lootweapons.common.data.LootData;

import java.awt.*;

public interface LootItem {
    
    RandomSource rand = RandomSource.createNewThreadLocalInstance();
    
    default ItemStack generate(int base, LootContext context) {
        return generate(getLuckFromContext(base, context));
    }
    
    default ItemStack generate(int luck) {
        double rarity = getRarityValue(luck);
        return generate(getRarity(rarity), luck, rarity);
    }
    
    ItemStack generate(Rarity randomRarity, int luck, double rarityValue);
    
    //feels like a pretty good distribution, mostly common and uncommon for luck < 2, starts distributing rares, legendaries and mythicals later
    //allows for a general distribution with values weighted to the current tier, while allowing commons to always generate, but weighting away from them
    //may have to tweak the distribution slightly so that generating above the upper bound is preferred instead of below
    //from rough testing with a pretty big sample size of 10000 runs approximate rarities for each luck value
    //starts at 0.9 for luck 0 aproximately 0.4 - 0.6 gain per luck level
    //may need to tweak the rarity values or the luck multiplier with testing
    default double getRarityValue(int luck) {
        return rand.nextInt(luck + 3) - (rand.nextInt(luck + 1) * rand.nextGaussian());
    }
    
    default Rarity getRarity(double value) {
        if (value > 13) return Constants.MYTHICAL;
        if (value > 10) return Constants.LEGENDARY;
        if (value > 7) return Rarity.EPIC;
        if (value > 4) return Rarity.RARE;
        if (value > 1) return Rarity.UNCOMMON;
        return Rarity.COMMON;
    }
    
    //multiply luck and looting modifiers by the base to hopefully make a luck value in a good range
    default int getLuckFromContext(int base, LootContext context) {
        return (int) ((double)base * ((context.getLootingModifier() + 1) * 0.5f) * context.getLuck());
    }
    
    default int getRandomMaterialColour(double rarityValue) {
        //pick a random hue
        float hue = rand.nextInt(360) * 0.00277777f;
        //higher rarity items should ideally look more saturated than lower ones to make them look more unique and less dull
        //clamp rarity to not overapply or underapply saturation
        int r = (int) Mth.clamp(rarityValue, 25, 1);
        //should proportionally weight to the higher bounds
        float saturation = (rand.nextInt(r) +  rand.nextInt(r) + rand.nextInt(r) + rand.nextInt(r)) * 0.01f;
        //random brightness, don't want it below 40 so the texture isn't too dark
        float brightness = rand.nextInt(rand.nextInt(60) + 40) * 0.01f;
        return Color.HSBtoRGB(hue, saturation, brightness);
    }
    
    //random distribution to calculate if a weapon should have an elemental attribute
    //weights rarity as well as initial luck, should usually favour luck unless the rolled weapon has a high rarity value
    default boolean shouldHaveElementalAttribute(int luck, double rarityValue) {
        return (rand.nextInt(luck + 3) * rand.nextInt((int) rarityValue)) * rand.nextGaussian() > 5;
    }
    
    //generate a descriptor used in the item toolip following the format rarity element weapon-type
    //e.g. Common Sword, Legendary Blazing Axe, Epic Icy Spear, Common Poisonous Knife, Mythical Chestplate
    default MutableComponent getDescription(ItemStack stack, Component name) {
        MutableComponent description = Component.translatable("text.lootweapons.rarity." + getRarity(stack).name()).append(" ");
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
        return LootData.fromTag(stack.getTag());
    }
    
}
