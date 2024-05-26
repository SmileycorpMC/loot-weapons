package net.smileycorp.lootweapons.client;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import net.smileycorp.lootweapons.common.data.LootData;
import net.smileycorp.lootweapons.common.item.LootItem;

public class LootWeaponColourHandler implements ItemColor {
    
    @Override
    public int getColor(ItemStack stack, int index) {
        if (stack.getItem() instanceof LootItem) {
            LootData data = ((LootItem) stack.getItem()).getLootData(stack);
            int[] colours = data.getColours();
            if (index < colours.length) return colours[index];
        }
        return 0xFFFFFFFF;
    }
    
}
