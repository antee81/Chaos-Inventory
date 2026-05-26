package chaosinventory;

import chaosinventory.config.ChaosConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChaosTimer {

    private static final Map<UUID, Integer> playerTimers = new HashMap<>();
    private static final Map<UUID, Boolean> playerActive = new HashMap<>();
    private static final int MIN_PLAYERS_MULTIPLAYER = 2;
    private static boolean globalEnabled = true;
    private static boolean globalPaused = false;

    private static int getDurationTicks() {
        return ChaosConfig.getChaosDurationTicks();
    }

    public static void initPlayer(UUID uuid) {
        playerTimers.put(uuid, getDurationTicks());
        playerActive.put(uuid, true);
        ChaosInventory.LOGGER.info("✅ Timer initialized for player: " + uuid + " length: " + getDurationTicks() / 20 + " secondi");
    }

    public static void removePlayer(UUID uuid) {
        playerTimers.remove(uuid);
        playerActive.remove(uuid);
    }

    public static void pausePlayer(UUID uuid) {
        playerActive.put(uuid, false);
    }

    public static void resumePlayer(UUID uuid) {
        playerActive.put(uuid, true);
    }

    public static void resetPlayer(UUID uuid) {
        playerTimers.put(uuid, getDurationTicks());
    }

    public static int getTicksRemaining(UUID uuid) {
        return playerTimers.getOrDefault(uuid, getDurationTicks());
    }

    public static boolean isRunning(UUID uuid) {
        return playerActive.getOrDefault(uuid, false);
    }

    public static String getTimeFormatted(UUID uuid) {
        int totalSeconds = getTicksRemaining(uuid) / 20;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static boolean isGlobalEnabled() {
        return globalEnabled;
    }

    public static void setGlobalEnabled(boolean enabled) {
        globalEnabled = enabled;
    }

    public static void tick(MinecraftServer server) {
        if (!globalEnabled) return;
        if (globalPaused) return;

        int playerCount = server.getPlayerCount();
        boolean isSingleplayer = server.isSingleplayer();


        boolean canRun;
        if (playerCount == 0) {
            canRun = false;
        } else if (isSingleplayer) {
            canRun = true;
        } else {
            canRun = playerCount >= MIN_PLAYERS_MULTIPLAYER;
        }

        if (!canRun) return;


        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            UUID uuid = player.getUUID();


            if (!playerTimers.containsKey(uuid)) {
                initPlayer(uuid);
            }


            if (player.isDeadOrDying() || !player.isAlive()) {
                pausePlayer(uuid);
                continue;
            }


            if (!isRunning(uuid)) continue;


            int remaining = playerTimers.get(uuid) - 1;
            playerTimers.put(uuid, remaining);


            if (remaining <= 0) {
                ChaosEvent event = ChaosRegistry.getRandomEvent();
                resetPlayer(uuid);
                ChaosInventory.LOGGER.info("⏰ Timer expired! New cycle of " + getDurationTicks() / 20 + " seconds for " + player.getName().getString());
            }
        }
    }
}
