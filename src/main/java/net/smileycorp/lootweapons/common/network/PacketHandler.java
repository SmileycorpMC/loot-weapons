package net.smileycorp.lootweapons.common.network;

import net.minecraft.network.Connection;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.smileycorp.atlas.api.network.AbstractMessage;
import net.smileycorp.atlas.api.network.NetworkUtils;
import net.smileycorp.lootweapons.common.Constants;

public class PacketHandler {
    
    private static SimpleChannel NETWORK_INSTANCE;
    
    public static void sendTo(AbstractMessage message, Connection manager, NetworkDirection direction) {
        NETWORK_INSTANCE.sendTo(message, manager, direction);
    }
    
    public static void send(PacketDistributor.PacketTarget target, AbstractMessage message) {
        NETWORK_INSTANCE.send(target, message);
    }
    
    public static void initPackets() {
        NETWORK_INSTANCE = NetworkUtils.createChannel(Constants.loc("elites"));
        NetworkUtils.registerMessage(NETWORK_INSTANCE,0, SyncAffixMessage.class);
    }
    
}
