package net.smileycorp.lootweapons.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.lootweapons.common.item.LootSword;

@Mod(value = Constants.MODID)
@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LootWeapons {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Constants.MODID);
	
	private static final RegistryObject<Item> LOOT_SWORD = ITEMS.register("loot_sword", LootSword::new);
	private static final RegistryObject<Item> LOOT_KNIFE = null;
	private static final RegistryObject<Item> LOOT_AXE = null;
	private static final RegistryObject<Item> LOOT_HAMMER = null;
	private static final RegistryObject<Item> LOOT_SPEAR = null;
	private static final RegistryObject<Item> LOOT_BOW = null;
	private static final RegistryObject<Item> LOOT_HELMET = null;
	private static final RegistryObject<Item> LOOT_CHESTPLATE = null;
	private static final RegistryObject<Item> LOOT_LEGGINGS = null;
	private static final RegistryObject<Item> LOOT_BOOTS = null;

	public LootWeapons() {
		LootWeaponsLogger.clearLog();
	}

	@SubscribeEvent
	public static void constructMod(FMLConstructModEvent event) {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
	
	}

	@SubscribeEvent
	public static void loadClient(FMLClientSetupEvent event) {
	
	}

}
