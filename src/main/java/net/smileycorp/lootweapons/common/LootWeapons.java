package net.smileycorp.lootweapons.common;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.smileycorp.lootweapons.common.network.PacketHandler;

@Mod(value = Constants.MODID)
@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LootWeapons {

	public LootWeapons() {
		LootWeaponsLogger.clearLog();
		PacketHandler.initPackets();
	}

	@SubscribeEvent
	public static void constructMod(FMLConstructModEvent event) {
	
	}

	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
	
	}

	@SubscribeEvent
	public static void loadClient(FMLClientSetupEvent event) {
	
	}

}
