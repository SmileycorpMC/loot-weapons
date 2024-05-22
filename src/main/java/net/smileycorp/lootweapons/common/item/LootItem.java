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
    
    default int getLuckFromContext(int base, LootContext context) {
        return (int) ((double)base * ((context.getLootingModifier() + 1) * 0.5f) * context.getLuck());
    }
    
    default int getRandomMaterialColour(double rarityValue) {
        float hue = rand.nextInt(360) * 0.00277777f;
        int r = (int) Mth.clamp(rarityValue, 25, 1);
        float saturation = (rand.nextInt(r) +  rand.nextInt(r) + rand.nextInt(r) + rand.nextInt(r)) * 0.01f;
        float brightness = rand.nextInt(rand.nextInt(60) + 40) * 0.01f;
        return Color.HSBtoRGB(hue, saturation, brightness);
    }
    
    default boolean shouldHaveElementalAttribute(int luck, double rarityValue) {
        return (rand.nextInt(luck + 3) * rand.nextInt((int) rarityValue)) * rand.nextGaussian() > 5;
    }
    
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
