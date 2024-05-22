package net.smileycorp.lootweapons.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.smileycorp.lootweapons.common.attributes.ElementalWeaponAttributes;
import net.smileycorp.lootweapons.common.data.LootData;

import javax.annotation.Nullable;
import java.util.List;

public class LootSword extends SwordItem implements LootItem {
    
    public LootSword() {
        super(Tiers.IRON, 3, -2.4F, new Item.Properties());
    }
    
    @Override
    public ItemStack generate(Rarity rarity, int luck, double rarityValue) {
        ItemStack stack = new ItemStack(this);
        LootData.Builder data = new LootData.Builder(rarity);
        data.colours(getRandomMaterialColour(rarityValue));
        data.parts((byte) 0, (byte) 0 , (byte) 0);
        data.stats(1f, 1f);
        if (shouldHaveElementalAttribute(luck, rarityValue)) data.attribute(ElementalWeaponAttributes.getRandomAttribute(rand));
        stack.setTag(data.build().toTag());
        return stack;
    }
    
    @Override
    public Rarity getRarity(ItemStack stack) {
        return LootItem.super.getRarity(stack);
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag) {
        lines.add(getDescription(stack, Component.translatable("text.lootweapons.item.sword")));
    }
    
}
