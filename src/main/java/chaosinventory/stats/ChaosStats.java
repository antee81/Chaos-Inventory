package chaosinventory.stats;

import chaosinventory.ChaosInventory;
import chaosinventory.data.DataManager;
import chaosinventory.economy.ChaosEconomy;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ChaosStats {
    private static final int XP_COMMON = 5;
    private static final int XP_RARE = 10;
    private static final int XP_EPIC = 25;
    private static final int XP_LEGENDARY = 50;  // ← corretto: LEGENDARY

    private static final int XP_PER_LEVEL = 100;
    private static final int MAX_LEVEL = 100;

    public static final Map<Integer, String> REWARDS = new HashMap<>();

    static {
        REWARDS.put(5, "§a+5% chance for positive events");
        REWARDS.put(10, "§bTimer reduced to 25 minutes");
        REWARDS.put(15, "§dUnlocked: Exclusive Event 'Luck Boost'");
        REWARDS.put(20, "§e+10% XP collected");
        REWARDS.put(25, "§6Unlocked: Title '[Chaos Veteran]'");
        REWARDS.put(30, "§5Timer reduced to 20 minutes");
        REWARDS.put(40, "§cUnlocked: Exclusive Event 'Chaos Storm'");
        REWARDS.put(50, "§4§lTitle '[§c§lCHOSEN ONE§4§l]'");
        REWARDS.put(75, "§6Unlocked: Pet Chaos");
        REWARDS.put(100, "§k§lCHAOS GOD §r§6§l- All effects duplicated");
    }

    public static void initPlayer(ServerPlayer player) {
        UUID uuid = player.getUUID();
        DataManager.loadPlayerData(uuid);  // ← SOLO caricamento, niente xp qui!
        System.out.println("Player data loaded for: " + player.getName().getString());
    }

    public static void addXP(ServerPlayer player, int xp, String eventName) {
        UUID uuid = player.getUUID();
        DataManager.PlayerData data = DataManager.loadPlayerData(uuid);

        int currentXP = data.xp;
        int currentLevel = data.level;
        int newXP = currentXP + xp;

        data.xp = newXP;
        data.totalEvents++;

        data.eventCounts.put(eventName, data.eventCounts.getOrDefault(eventName, 0) + 1);

        int newLevel = 1 + (newXP / XP_PER_LEVEL);
        if (newLevel > MAX_LEVEL) newLevel = MAX_LEVEL;

        if (newLevel > currentLevel) {
            data.level = newLevel;
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§a§l✨ LEVEL UP! §r§aYou reached level §e" + newLevel + "§a! ✨"));
            System.out.println(player.getName().getString() + " reached level " + newLevel);
            onLevelUp(player, newLevel, data);
        }

        DataManager.updatePlayerData(uuid, data);
    }

    public static void onLevelUp(ServerPlayer player, int level, DataManager.PlayerData data) {
        checkUnlocks(player, level, data);

        if (level %10 == 0) {
            ChaosEconomy.addCoins(player, 100);
        }

        if (level % 10 == 0) {
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§6§l🏆 GOAL ACHIEVED! §r§6You reached level §e" + level + "§6!"));
        }
    }

    private static void checkUnlocks(ServerPlayer player, int level, DataManager.PlayerData data) {
        for (Map.Entry<Integer, String> entry : REWARDS.entrySet()) {
            if (level >= entry.getKey() && !data.unlockedRewards.containsKey(entry.getKey().toString())) {
                data.unlockedRewards.put(entry.getKey().toString(), true);
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§6§l🏆 REWARD UNLOCKED! §r§6" + entry.getValue()));
                System.out.println(player.getName().getString() + " unlocked: " + entry.getValue());
            }
        }
    }

    public static int getXPForEvent(int weight) {
        if (weight < 5) return XP_LEGENDARY;
        if (weight < 15) return XP_EPIC;
        if (weight < 30) return XP_RARE;
        return XP_COMMON;
    }

    public static int getPlayerXP(ServerPlayer player) {
        DataManager.PlayerData data = DataManager.loadPlayerData(player.getUUID());
        return data.xp;
    }

    public static int getPlayerLevel(ServerPlayer player) {
        DataManager.PlayerData data = DataManager.loadPlayerData(player.getUUID());
        return data.level;
    }

    public static int getPlayerTotalEvents(ServerPlayer player) {
        DataManager.PlayerData data = DataManager.loadPlayerData(player.getUUID());
        return data.totalEvents;
    }

    public static int getXPToNextLevel(ServerPlayer player) {  // ← corretto nome metodo
        int xp = getPlayerXP(player);
        int currentLevel = getPlayerLevel(player);
        int nextLevelXP = currentLevel * XP_PER_LEVEL;
        return nextLevelXP - xp;
    }

    public static int getProgressPercent(ServerPlayer player) {
        int xp = getPlayerXP(player);
        int currentLevel = getPlayerLevel(player);
        int currentLevelXP = (currentLevel - 1) * XP_PER_LEVEL;
        int xpInLevel = xp - currentLevelXP;
        return (xpInLevel * 100) / XP_PER_LEVEL;
    }

    public static Map<String, Integer> getEventCounts(ServerPlayer player) {
        DataManager.PlayerData data = DataManager.loadPlayerData(player.getUUID());
        return data.eventCounts;
    }

    public static Set<String> getUnlockedRewards(ServerPlayer player) {
        DataManager.PlayerData data = DataManager.loadPlayerData(player.getUUID());
        return data.unlockedRewards.keySet();
    }

    public static String getRewardDescription(int level) {
        return REWARDS.getOrDefault(level, "No reward");
    }

    public static void removePlayer(UUID uuid) {
        DataManager.removeFromCache(uuid);
    }
}
