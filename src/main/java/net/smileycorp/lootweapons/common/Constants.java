package net.smileycorp.lootweapons.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;

public class Constants {
	
	public static final Rarity LEGENDARY = Rarity.create("legendary", style -> style.withColor(0xFFC300).withBold(true));
	public static final Rarity MYTHICAL = Rarity.create("mythic", style -> style.withColor(0xFF9300).withBold(true));

	public static final String MODID = "lootweapons";
	public static final String NAME = "Loot Weapons";

	public static String name(String name) {
		return name(MODID, name);
	}

	public static String name(String modid, String name) {
		return modid + "." + name.replace("_", "");
	}

	public static ResourceLocation loc(String name) {
		return new ResourceLocation(MODID, name.toLowerCase());
	}

	public static String locStr(String string) {
		return loc(string).toString();
	}

}
