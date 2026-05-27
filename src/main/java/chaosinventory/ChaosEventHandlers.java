package chaosinventory;

import chaosinventory.client.ChaosStatsHUD;
import chaosinventory.afk.AfkManager;
import chaosinventory.stats.ChaosStats;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import chaosinventory.afk.AfkManager;
import net.minecraftforge.event.TickEvent;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = ChaosInventory.MODID)
public class ChaosEventHandlers {

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        MinecraftServer server = event.getServer();
        if (server == null) return;

        ChaosTimer.tick(server);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        boolean hasMoved = player.xo != player.getX() || player.zo != player.getZ();
        boolean hasRotated = player.xRotO != player.getXRot() || player.yRotO != player.getYRot();

        if (hasMoved || hasRotated) {
            AfkManager.updateActivity(player);
        }
        AfkManager.checkPlayer(player);
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ChaosStats.initPlayer(player);
            AfkManager.updateActivity(player);
            ChaosTimer.initPlayer(player.getUUID());
            ChaosStatsHUD.showMessage(player.getUUID());
            ChaosInventory.LOGGER.info("\uD83C\uDF00 Player joined: " + player.getName().getString());
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AfkManager.removePlayer(player.getUUID());
            ChaosTimer.removePlayer(player.getUUID());
            ChaosInventory.LOGGER.info("\uD83C\uDF00 Player left: " + player.getName().getString());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            AfkManager.updateActivity(player);
            ChaosTimer.resumePlayer(player.getUUID());
            ChaosInventory.LOGGER.info("\uD83C\uDF00 Player respawned, timer resumed: " + player.getName().getString());
        }
    }
}
