package chaosinventory.network;

import chaosinventory.ChaosInventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ChaosNetwork {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ChaosInventory.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        INSTANCE.messageBuilder(BuyItemPacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(BuyItemPacket::encode)
                .decoder(BuyItemPacket::new)
                .consumerMainThread(BuyItemPacket::handle)
                .add();

        System.out.println("\uD83C\uDF10 Network packets registered");
    }
}
