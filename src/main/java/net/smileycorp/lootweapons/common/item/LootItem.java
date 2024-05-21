package net.smileycorp.lootweapons.common.item;

import net.minecraft.world.item.ItemStack;
import net.smileycorp.lootweapons.common.data.LootData;

public interface LootItem {
    
    static LootData getLootData(ItemStack stack) {
        return LootData.fromTag(stack.getTag());
    }
    
}
