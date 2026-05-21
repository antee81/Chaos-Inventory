package chaosinventory;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChaosInventory.MODID)
public class ChaosEvents {
    private static final int MIN_PLAYERS_MULTIPLAYER = 2;

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        MinecraftServer server = event.getServer();
        if (server == null) return;
        int playerCount = server.getPlayerCount();
        boolean isSingleplayer = server.isSingleplayer();

        if (playerCount == 0) {
            ChaosTimer.stop();
        } else if (isSingleplayer) {
            ChaosTimer.start();
        } else if (playerCount >= MIN_PLAYERS_MULTIPLAYER) {
            ChaosTimer.start();
        }

        ChaosTimer.tick(server);
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        ChaosInventory.LOGGER.info("\uD83C\uDF00 Player joined: " + event.getEntity().getName().getString());
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        ChaosInventory.LOGGER.info("\uD83C\uDF00 Player disconnected: " + event.getEntity().getName().getString());
    }
}
