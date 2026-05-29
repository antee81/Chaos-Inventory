package chaosinventory.afk;

import chaosinventory.ChaosInventory;
import chaosinventory.ChaosTimer;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AfkManager {
    private static final Map<UUID, Long> lastActionTime = new HashMap<>();
    private static final Map<UUID, Boolean> wasAfk = new HashMap<>();
    private static final int AFK_THRESHOLD_SECONDS = 300;
    private static boolean afkPausesTimer = true;

    public static void updateActivity(ServerPlayer player) {
        UUID uuid = player.getUUID();
        lastActionTime.put(uuid, System.currentTimeMillis());

        if (wasAfk.getOrDefault(uuid, false)) {
            wasAfk.put(uuid, false);
            ChaosTimer.resumePlayer(uuid);
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§a✅ You are no longer AFK! Chaos Timer resumed."));
        }
    }

    public static boolean isAfk(ServerPlayer player) {
        UUID uuid = player.getUUID();
        long lastAction = lastActionTime.getOrDefault(uuid, System.currentTimeMillis());
        long timeSinceLastAction = (System.currentTimeMillis() - lastAction) / 1000;
        return timeSinceLastAction >= AFK_THRESHOLD_SECONDS;
    }

    public static void checkPlayer(ServerPlayer player) {
        if (!afkPausesTimer) return;

        UUID uuid = player.getUUID();
        boolean isAfk = isAfk(player);
        boolean wasAfkBefore = wasAfk.getOrDefault(uuid, false);

        if (isAfk && !wasAfkBefore) {
            wasAfk.put(uuid, true);
            ChaosTimer.pausePlayer(uuid);
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§c⏸️ You're AFK! Timer will resume when you start moving."));
            System.out.println("[CHAOS] " + player.getName().getString() + " is now AFK - timer paused");
        }
    }  // ← CHIUSURA CORRETTA di checkPlayer

    public static void removePlayer(UUID uuid) {
        lastActionTime.remove(uuid);
        wasAfk.remove(uuid);
    }
}
