package net.smileycorp.lootweapons.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
        data.stats(calculateAttributeMultiplier(rarityValue), calculateAttributeMultiplier(rarityValue));
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
    
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        float[] stats = getLootData(stack).getStats();
        float attack = (stats.length > 0) ? stats[0] : 1;
        float speed = (stats.length > 1) ? 1-stats[1] : 1;
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attack * 5f, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", speed * -2.4f, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
    
}
