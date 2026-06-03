package chaosinventory.achievements;

import chaosinventory.advancements.AdvancementHelper;
import chaosinventory.data.DataManager;
import chaosinventory.stats.ChaosStats;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

import java.util.*;

public class AchievementManager {

    private static final List<Achievement> ACHIEVEMENTS = new ArrayList<>();
    private static final Map<UUID, Set<String>> unlockedAchievements = new HashMap<>();

    public static class Achievement {
        public String id;
        public String name;
        public String description;
        public String icon;
        public int requiredValue;
        public String type;

        public Achievement(String id, String name, String description, String icon, int requiredValue, String type) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.icon = icon;
            this.requiredValue = requiredValue;
            this.type = type;
        }
    }

    static {
        ACHIEVEMENTS.add(new Achievement("event_10", "§aEvent Survivor", "Survive 10 chaos events", "diamond", 10, "events"));
        ACHIEVEMENTS.add(new Achievement("event_50", "§aEvent Veteran", "Survive 50 chaos events", "gold_block", 50, "events"));
        ACHIEVEMENTS.add(new Achievement("event_100", "§aChaos Legend", "Survive 100 chaos events", "nether_star", 100, "events"));

        ACHIEVEMENTS.add(new Achievement("level_10", "Rising Star", "Reach level 10", "experience_bottle", 10, "level"));
        ACHIEVEMENTS.add(new Achievement("level_25", "§aChaos Adept", "Reach level 25", "enchanting_table", 25, "level"));
        ACHIEVEMENTS.add(new Achievement("level_50", "§aChaos Master", "Reach level 50", "dragon_head", 50, "level"));
        ACHIEVEMENTS.add(new Achievement("level_100", "§aChaos God", "Reach level 100", "beacon", 100, "level"));

        ACHIEVEMENTS.add(new Achievement("xp_1000", "§aXP Novice", "Earn 1000 XP", "experience_bottle", 1000, "xp"));
        ACHIEVEMENTS.add(new Achievement("xp_5000", "§aXP Expert", "Earn 5000 XP", "enchanted_book", 5000, "xp"));
        ACHIEVEMENTS.add(new Achievement("xp_10000", "§aXP Master", "Earn 10000 XP", "beacon", 10000, "xp"));

        ACHIEVEMENTS.add(new Achievement("coins_500", "§aRich", "Accumulate 500 coins", "gold_ingot", 500, "coins"));
        ACHIEVEMENTS.add(new Achievement("coins_2000", "§aMillionaire", "Accumulate 2000 coins", "gold_block", 2000, "coins"));
        ACHIEVEMENTS.add(new Achievement("coins_10000", "§aChaos Tycoon", "Accumulate 10000 coins", "emerald_block", 10000, "coins"));

        ACHIEVEMENTS.add(new Achievement("quest_5", "§aQuest Starter", "Complete 5 quests", "book", 5, "quest"));
        ACHIEVEMENTS.add(new Achievement("quest_20", "§aQuest Hero", "Complete 20 quests", "written_book", 20, "quest"));

        ACHIEVEMENTS.add(new Achievement("teleport_10", "§aLost Wanderer", "Get teleported 10 times", "ender_pearl", 10, "teleport"));
        ACHIEVEMENTS.add(new Achievement("diamond_lover", "§aDiamond Lover", "Get the Diamonds event 5 times", "diamond", 5, "diamond_event"));
        ACHIEVEMENTS.add(new Achievement("tnt_maniac", "§aTNT Maniac", "Get the TNT event 5 times", "tnt", 5, "tnt_event"));
    }

    public static void init() {
        AdvancementHelper.init();
    }

    public static void initPlayer(ServerPlayer player) {
        UUID uuid = player.getUUID();
        unlockedAchievements.putIfAbsent(uuid, new HashSet<>());
    }

    public static void checkAndUnlock(ServerPlayer player, String type, int value) {
        UUID uuid = player.getUUID();
        Set<String> unlocked = unlockedAchievements.get(uuid);
        if (unlocked == null) {
            unlocked = new HashSet<>();
            unlockedAchievements.put(uuid, unlocked);
        }

        for (Achievement ach : ACHIEVEMENTS) {
            if (ach.type.equals(type) && value >= ach.requiredValue && !unlocked.contains(ach.id)) {
                unlockAchievement(player, ach);
                unlocked.add(ach.id);
            }
        }

        saveAchievements(player);
    }

    private static void unlockAchievement(ServerPlayer player, Achievement ach) {
        AdvancementHelper.unlock(player, ach.id, ach.name, ach.description);

        player.playNotifySound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE,
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);

        System.out.println("[ACHIEVEMENT] " + player.getName().getString() + " unlocked: " + ach.name);
    }

    public static List<String> getAchievementList(ServerPlayer player) {
        UUID uuid = player.getUUID();
        Set<String> unlocked = unlockedAchievements.getOrDefault(uuid, new HashSet<>());
        List<String> result = new ArrayList<>();

        for (Achievement ach : ACHIEVEMENTS) {
            String status;
            if (unlocked.contains(ach.id)) {
                status = "§a✔ COMPLETED";
            } else {
                status = "§c✘ LOCKED";
            }
            result.add("§7- " + ach.name + " §8[" + status + "§8]");
            result.add("§8  " + ach.description);
            result.add("");
        }

        return result;
    }

    public static int getProgress(ServerPlayer player, String type) {
        switch (type) {
            case "events": return ChaosStats.getPlayerTotalEvents(player);
            case "level": return ChaosStats.getPlayerLevel(player);
            case "xp": return ChaosStats.getPlayerXP(player);
            default: return 0;
        }
    }

    public static void saveAchievements(ServerPlayer player) {
        UUID uuid = player.getUUID();
        DataManager.saveAchievements(uuid, unlockedAchievements.getOrDefault(uuid, new HashSet<>()));
    }

    public static void loadAchievements(ServerPlayer player) {
        UUID uuid = player.getUUID();
        Set<String> loaded = DataManager.loadAchievements(uuid);
        if (loaded != null) {
            unlockedAchievements.put(uuid, loaded);
        } else {
            unlockedAchievements.put(uuid, new HashSet<>());
        }
    }
}