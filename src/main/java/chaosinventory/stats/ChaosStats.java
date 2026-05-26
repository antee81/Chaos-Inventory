package chaosinventory.stats;

import chaosinventory.ChaosInventory;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

public class ChaosStats {
    private static final int XP_COMMON = 5;
    private static final int XP_RARE = 10;
    private static final int XP_EPIC = 25;
    private static final int XP_LEGGENDARY = 50;

    private static final int XP_PER_LEVEL = 100;
    private static final int MAX_LEVEL = 100;

    private static final Map<UUID, Integer> playerXP = new HashMap<>();
    private static final Map<UUID, Integer> playerLevel = new HashMap<>();
    private static final Map<UUID, Integer> totalEvents = new HashMap<>();
    private static final Map<UUID, Map<String, Integer>> eventCounts = new HashMap<>();

    private static final Map<UUID, Set<String>> unlockedRewards = new HashMap<>();

    public static final Map<Integer, String> REWARDS = new HashMap<>();

    static {
        REWARDS.put(5, "§a+5% chance for positive events");
        REWARDS.put(10, "§bTimer reduced to 25 minutes");
        REWARDS.put(15, "§dUnlocked: Exclusive Event 'Luck Boost'");
        REWARDS.put(20, "§e+10% XP collected");
        REWARDS.put(25, "§6Unlocked: Title '[Chaos Veteran]'");
        REWARDS.put(30, "§5Timer reduced to 20 minutes");
        REWARDS.put(40, "§cUnlocked: Exclusive Event 'Chaos Storm'");
        REWARDS.put(50, "§4§lTitle '[§c§lCHOSEN ONE §4§l]'");
        REWARDS.put(75, "§6Unlocked: Pet Chaos");
        REWARDS.put(100, "§k§lCHAOS GOD §r§6§l- All the effect have been duplicated");
    }

    public static void initPlayer(ServerPlayer player) {
        UUID uuid = player.getUUID();
        playerXP.putIfAbsent(uuid, 0);
        playerLevel.putIfAbsent(uuid, 1);
        totalEvents.putIfAbsent(uuid, 0);
        eventCounts.putIfAbsent(uuid, new HashMap<>());
    }

    public static void addXP(ServerPlayer player, int xp, String eventName) {
        UUID uuid = player.getUUID();
        int currentXP = playerXP.getOrDefault(uuid, 0);
        int currentLevel = playerLevel.getOrDefault(uuid, 1);
        int newXP = currentXP + xp;

        playerXP.put(uuid, newXP);
        totalEvents.put(uuid, totalEvents.getOrDefault(uuid, 0) + 1);

        Map<String, Integer> counts = eventCounts.get(uuid);
        counts.put(eventName, counts.getOrDefault(eventName, 0) + 1);

        int newLevel = 1 + (newXP / XP_PER_LEVEL);
        if (newLevel > MAX_LEVEL) newLevel = MAX_LEVEL;

        if (newLevel > currentLevel) {
            playerLevel.put(uuid, newLevel);
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§a§l✨ LEVEL UP! §r§aYou leveled up to §e" + newLevel + "§a!✨"));
            ChaosInventory.LOGGER.info(player.getName().getString() + " reached level " + newLevel);
            onLevelUp(player, newLevel);
        }

        ChaosInventory.LOGGER.debug(player.getName().getString() + " gained " + xp + " XP from " + eventName);
    }

    private static void onLevelUp(ServerPlayer player, int level) {
        if (level % 10 == 0) {
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§6§l\uD83C\uDFC6 MILESTONE REACHED! §r§6You reached level §e" + level + "§6!"));
        }
    }

    public static int getXPForEvent(int weight) {
        if (weight < 5) return XP_LEGGENDARY;
        if (weight < 15) return XP_EPIC;
        if (weight < 30) return XP_RARE;
        return XP_COMMON;
    }

    public static int getPlayerXP(ServerPlayer player) {
        return playerXP.getOrDefault(player.getUUID(), 0);
    }

    public static int getPlayerLevel(ServerPlayer player) {
        return playerLevel.getOrDefault(player.getUUID(), 0);
    }

    public static int getXPToNextLevel(ServerPlayer player) {
        int xp = getPlayerXP(player);
        int currentLevel = getPlayerLevel(player);
        int nextLevelXP = currentLevel * XP_PER_LEVEL;
        return nextLevelXP - xp;
    }

    public static Map<String, Integer> getEventCounts(ServerPlayer player)  {
        return eventCounts.getOrDefault(player.getUUID(), new HashMap<>());
    }

    public static int getPlayerTotalEvents(ServerPlayer player) {
        return totalEvents.getOrDefault(player.getUUID(), 0);
    }

    public static void removePlayer(UUID uuid) {

    }

    public static int getProgressPercent(ServerPlayer player) {
        int xp = getPlayerXP(player);
        int currentLevel = getPlayerLevel(player);
        int currentLevelXP = (currentLevel - 1) * XP_PER_LEVEL;
        int xpInLevel = xp - currentLevelXP;
        return (xpInLevel * 100) / XP_PER_LEVEL;
    }

    public static Set<String> getUnlockedRewards(ServerPlayer player) {
        return unlockedRewards.getOrDefault(player.getUUID(), new HashSet<>());
    }

    public static String getRewardDescription(int level) {
        return REWARDS.getOrDefault(level, "No reward");
    }
}
