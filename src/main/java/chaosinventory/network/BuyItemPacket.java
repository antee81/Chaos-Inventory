package chaosinventory.network;

import chaosinventory.economy.ChaosEconomy;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BuyItemPacket {

    private final String itemId;

    public BuyItemPacket(String itemId) {
        this.itemId = itemId;
    }

    public BuyItemPacket(FriendlyByteBuf buf) {
        this.itemId = buf.readUtf();
    }

    public static void encode(BuyItemPacket packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.itemId);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            ChaosEconomy.buyItem(player, itemId, 1);
        });
        ctx.get().setPacketHandled(true);
    }
}
