package net.smileycorp.lootweapons.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;

import java.util.Locale;

public class Constants {
	
	public static final Rarity LEGENDARY = Rarity.create("LEGENDARY", style -> style.withColor(0xFFC300).withBold(true));
	public static final Rarity MYTHICAL = Rarity.create("MYTHICAL", style -> style.withColor(0xFF9300).withBold(true));

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
	
	public static Rarity getRarity(String name) {
		name = name.toLowerCase(Locale.US);
		if (name.equals("mythical")) return Constants.MYTHICAL;
		if (name.equals("legendary")) return Constants.LEGENDARY;
		if (name.equals("epic")) return Rarity.EPIC;
		if (name.equals("rare")) return Rarity.RARE;
		if (name.equals("uncommon")) return Rarity.UNCOMMON;
		return Rarity.COMMON;
	}

}
