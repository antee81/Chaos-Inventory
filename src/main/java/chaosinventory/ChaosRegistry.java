package chaosinventory;

import chaosinventory.events.*;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChaosRegistry {
    private static final List<ChaosEvent> EVENTS = new ArrayList<>();
    private static final Random RANDOM = new Random();

    public static void registerAll() {
        EVENTS.clear();
        EVENTS.add(new DiamondsEvent());
        EVENTS.add(new TntEvent());
        EVENTS.add(new PoisonousPotatoEvent());

        ChaosInventory.LOGGER.info("\uD83C\uDF00 Register " + EVENTS.size() + " eventi del Caos");
    }

    public static ChaosEvent getRandomEvent() {
        if (EVENTS.isEmpty()) return null;

        int totalWeight = 0;
        for (ChaosEvent event : EVENTS) {
            totalWeight += event.getWeight();
        }

        int random = RANDOM.nextInt(totalWeight);
        int current = 0;
        for (ChaosEvent event : EVENTS) {
            current += event.getWeight();
            if (random < current) {
                return event;
            }
        }

        return EVENTS.get(0);
    }

    public static void triggerRandomEventForAll(net.minecraft.server.MinecraftServer server) {
        ChaosEvent event = getRandomEvent();
        if (event == null) return;

        ChaosInventory.LOGGER.info("\uD83D\uDCA5 CHAOS: " + event.getName());

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            event.execute(player);
        }
    }
}
