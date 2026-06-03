package chaosinventory;

import chaosinventory.achievements.AchievementManager;
import chaosinventory.data.DataManager;
import chaosinventory.economy.ChaosEconomy;
import chaosinventory.quests.QuestManager;
import chaosinventory.stats.ChaosStats;
import chaosinventory.config.ChaosConfig;
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
        ChaosConfig.load();

        // === UTILI ===
        EVENTS.add(new DiamondsEvent());
        EVENTS.add(new GoldenAppleEvent());
        EVENTS.add(new GoldenCarrotEvent());
        EVENTS.add(new EnderPearlEvent());
        EVENTS.add(new ShieldEvent());
        EVENTS.add(new SpectralArrowsEvent());
        EVENTS.add(new DiamondBlockEvent());
        EVENTS.add(new StrengthPotionEvent());
        EVENTS.add(new InvisibilityPotionEvent());
        EVENTS.add(new NetheriteSwordEvent());
        EVENTS.add(new TotemEvent());
        EVENTS.add(new ElytraEvent());
        EVENTS.add(new InfinityBowEvent());
        EVENTS.add(new EfficiencyPickaxeEvent());
        EVENTS.add(new NetheriteArmorEvent());

        // === TROLL ===
        EVENTS.add(new PoisonousPotatoEvent());
        EVENTS.add(new DirtEvent());
        EVENTS.add(new ButtonEvent());
        EVENTS.add(new PumpkinSeedsEvent());
        EVENTS.add(new TropicalFishEvent());
        EVENTS.add(new EmptyMapEvent());
        EVENTS.add(new BoatEvent());
        EVENTS.add(new SaddleEvent());
        EVENTS.add(new IceEvent());
        EVENTS.add(new CakeEvent());
        EVENTS.add(new BrokenCompassEvent());
        EVENTS.add(new JukeboxEvent());
        EVENTS.add(new CreeperHeadEvent());
        EVENTS.add(new RunBookEvent());
        EVENTS.add(new BehindYouBookEvent());

        // === PERICOLOSI ===
        EVENTS.add(new TntEvent());
        EVENTS.add(new LavaBucketEvent());
        EVENTS.add(new HungerEffectEvent());
        EVENTS.add(new SlownessEffectEvent());
        EVENTS.add(new BlindnessEffectEvent());
        EVENTS.add(new DamagePotionEvent());
        EVENTS.add(new RandomTeleportEvent());
        EVENTS.add(new ShuffleInventoryEvent());
        EVENTS.add(new ShuffleHotbarEvent());

        // === SPAWN EGGS ===
        EVENTS.add(new ZombieEggsEvent());
        EVENTS.add(new EndermanEggsEvent());
        EVENTS.add(new PhantomEggsEvent());
        EVENTS.add(new CreeperEggsEvent());
        EVENTS.add(new VexEggsEvent());
        EVENTS.add(new WitherSkeletonEggsEvent());

        // === EVENTI EPICI ===
        EVENTS.add(new DiamondRainEvent());
        EVENTS.add(new JackpotEvent());
        EVENTS.add(new CowRainEvent());
        EVENTS.add(new RandomExplosionEvent());
        EVENTS.add(new EarthquakeEvent());

        EVENTS.add(new GravityFlipEvent());
        EVENTS.add(new ItemSwapperEvent());
        EVENTS.add(new TimeWarpEvent());
        EVENTS.add(new ChatSpamEvent());
        EVENTS.add(new FakeDeathEvent());
        EVENTS.add(new InvertControlsEvent());
        EVENTS.add(new RandomPotionEvent());
        EVENTS.add(new VillagerRainEvent());
        EVENTS.add(new LightningStrikeEvent());
        EVENTS.add(new InventoryVoidEvent());

        System.out.println("🌀 Registered " + EVENTS.size() + " Chaos events");
    }

    public static boolean triggerEventByName(ServerPlayer player, String eventName) {
        for (ChaosEvent event : EVENTS) {
            if (event.getName().equalsIgnoreCase(eventName)) {
                event.execute(player);
                System.out.println("\uD83D\uDCA5 Manual CHAOS for " + player.getName().getString() + ": " + event.getName());
                return true;
            }
        }
        return false;
    }

    public static void triggerRandomEventForPlayer(ServerPlayer player) {
        ChaosEvent event = getRandomEvent();
        if (event == null) return;
        if (player.server.isSingleplayer() && event.isMultiplayerOnly()) return;

        ChaosStats.initPlayer(player);

        QuestManager.updateProgress(player, "event_5", 1);
        QuestManager.updateProgress(player, "event_10", 1);

        if (event.getName().equals("Diamonds")) {
            QuestManager.updateProgress(player, "diamonds", 1);
        }
        if (event.getName().equals("TNT Donated")) {
            QuestManager.updateProgress(player, "tnt", 1);
        }
        if (event.getName().equals("Random Teleport")) {
            QuestManager.updateProgress(player, "teleport", 1);
        }

        System.out.println("\uD83D\uDCA5 CHAOS for " + player.getName().getString() + ": " + event.getName());
        event.execute(player);

        AchievementManager.checkAndUnlock(player, "events", ChaosStats.getPlayerTotalEvents(player));

        if (event.getName().equals("Diamonds")) {
            int diamondCount = DataManager.getDiamondEventCount(player.getUUID()) + 1;
            DataManager.setDiamondEventCount(player.getUUID(), diamondCount);
            AchievementManager.checkAndUnlock(player, "diamond_event", diamondCount);
        }
        if (event.getName().equals("TNT Donated")) {
            int tntCount = DataManager.getTNTEventCount(player.getUUID()) + 1;
            DataManager.setTNTEventCount(player.getUUID(), tntCount);
            AchievementManager.checkAndUnlock(player, "tnt_event", tntCount);
        }
        if (event.getName().equals("Random Teleport")) {
            int teleportCount = DataManager.getTeleportCount(player.getUUID()) + 1;
            DataManager.setTeleportCount(player.getUUID(), teleportCount);
            AchievementManager.checkAndUnlock(player, "teleport", teleportCount);
        }

        int xp = ChaosStats.getXPForEvent(event.getWeight());
        ChaosStats.addXP(player, xp, event.getName());

        QuestManager.updateProgress(player, "xp_100", xp);
        QuestManager.updateProgress(player, "xp_500", xp);

        boolean hasFreeSlots = false;
        for (int i = 0; i < 36; i++) {
            if (player.getInventory().getItem(i).isEmpty()) {
                hasFreeSlots = true;
                break;
            }
        }

        if (hasFreeSlots) {
            int coins = 5 + (event.getWeight() / 5);
            ChaosEconomy.addCoins(player, coins);
        } else {
            System.out.println("§c⚠\uFE0F Your inventory is full! No Chaos Coins for you today.");
        }
    }

    public static int getEventCount() {
        return EVENTS.size();
    }

    public static ChaosEvent getRandomEvent() {
        if (EVENTS.isEmpty()) return null;

        List<ChaosEvent> enabledEvents = new ArrayList<>();
        for (ChaosEvent event : EVENTS) {
            if (ChaosConfig.isEventEnabled(event.getName())) {
                enabledEvents.add(event);
            }
        }

        if (enabledEvents.isEmpty()) return null;

        int totalWeight = 0;
        for (ChaosEvent event : enabledEvents) {
            int weight = ChaosConfig.getEventWeight(event.getName(), event.getWeight());
            totalWeight += weight;
        }

        int random = RANDOM.nextInt(totalWeight);
        int current = 0;
        for (ChaosEvent event : enabledEvents) {
            int weight = ChaosConfig.getEventWeight(event.getName(), event.getWeight());
            current += weight;
            if (random < current) {
                return event;
            }
        }

        return enabledEvents.get(0);
    }
}
