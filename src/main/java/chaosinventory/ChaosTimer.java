package chaosinventory;

import it.unimi.dsi.fastutil.chars.Char2ObjectRBTreeMap;
import net.minecraft.server.MinecraftServer;

public class ChaosTimer {
    public static final int CHAOS_DURATION_TICKS = 20 * 20;
    private static int ticksRemaining = CHAOS_DURATION_TICKS;
    private static boolean running = false;
    public static void start() {
        if (!running) {
            running = true;
            ChaosInventory.LOGGER.info("\uD83C\uDF00 Chaos Timer STARTED!");
        }
    }

    public static void stop() {
        if (running) {
            running = false;
            ChaosInventory.LOGGER.info("\uD83C\uDF00 Chaos Timer STOPPED (no online players");
        }
    }

    public static void reset() {
        ticksRemaining = CHAOS_DURATION_TICKS;
    }

    public static void tick(MinecraftServer server) {
        if (!running) return;

        ticksRemaining--;

        if (ticksRemaining <= 0) {
            triggerChaos(server);
            reset();
        }
    }

    private static void triggerChaos(MinecraftServer server) {
        ChaosInventory.LOGGER.info("\uD83D\uDCA5 CHAOS HAS ARRIVED!");
        ChaosRegistry.triggerRandomEventForAll(server);
    }

    public static int getTicksRemaining() {
        return ticksRemaining;
    }

    public static boolean isRunning() {
        return running;
    }

    public static String getTimeFormatted() {
        int totalSeconds = ticksRemaining / 20;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
