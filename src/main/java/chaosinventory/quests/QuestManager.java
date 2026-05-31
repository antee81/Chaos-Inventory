package chaosinventory.quests;

import chaosinventory.ChaosInventory;
import chaosinventory.data.DataManager;
import chaosinventory.economy.ChaosEconomy;
import chaosinventory.stats.ChaosStats;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class QuestManager {

    private static final List<Quest> DAILY_QUESTS = new ArrayList<>();
    private static final Map<UUID, Map<String, Integer>> playerProgress = new HashMap<>();
    private static final Map<UUID, Set<String>> completedQuests = new HashMap<>();
    private static final Map<UUID, Long> lastResetTime = new HashMap<>();

    public static class Quest {
        public String id;
        public String name;
        public String description;
        public int target;
        public int rewardXP;
        public int rewardCoins;
        public ItemStack rewardItem;

        public Quest(String id, String name, String description, int target, int rewardXP, int rewardCoins, ItemStack rewardItem) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.target = target;
            this.rewardXP = rewardXP;
            this.rewardCoins = rewardCoins;
            this.rewardItem = rewardItem;
        }
    }

    static {
        DAILY_QUESTS.add(new Quest("event_5", "§eEvent Survivor", "§7Survive 5 chaos events", 5, 50, 10, new ItemStack(Items.DIAMOND, 1)));
        DAILY_QUESTS.add(new Quest("event_10", "§aEvent Master", "§7Survive 10 chaos events", 10, 100, 20, new ItemStack(Items.DIAMOND_BLOCK, 1)));

        DAILY_QUESTS.add(new Quest("xp_100", "§bXP Hunter", "§7Earn 100 XP", 100, 25, 5, new ItemStack(Items.EXPERIENCE_BOTTLE, 3)));
        DAILY_QUESTS.add(new Quest("xp_500", "§dXP Master", "§7Earn 500 XP", 500, 75, 15, new ItemStack(Items.EXPERIENCE_BOTTLE, 8)));

        DAILY_QUESTS.add(new Quest("diamonds", "§bDiamond Lover", "§7Receive the Diamonds event", 1, 30, 5, new ItemStack(Items.DIAMOND, 2)));
        DAILY_QUESTS.add(new Quest("tnt", "§cTNT Enjoyer", "§7Receive the TNT event", 1, 20, 3, new ItemStack(Items.TNT, 2)));  // ← Items.TNT, non Item.TNT

        DAILY_QUESTS.add(new Quest("teleport", "§5Lost Traveler", "§7Get teleported", 1, 25, 4, new ItemStack(Items.ENDER_PEARL, 2)));
    }

    public static void checkReset(ServerPlayer player) {
        UUID uuid = player.getUUID();
        long now = System.currentTimeMillis();
        long lastReset = lastResetTime.getOrDefault(uuid, 0L);

        if (now - lastReset > 24 * 60 * 60 * 1000) {
            playerProgress.put(uuid, new HashMap<>());
            completedQuests.put(uuid, new HashSet<>());
            lastResetTime.put(uuid, now);
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§a📅 Daily quests have been reset! Use §e/chaos quest§a to see your new challenges!"));
            System.out.println("Daily quests reset for " + player.getName().getString());
        }
    }

    public static void initPlayer(ServerPlayer player) {
        UUID uuid = player.getUUID();
        playerProgress.putIfAbsent(uuid, new HashMap<>());
        completedQuests.putIfAbsent(uuid, new HashSet<>());
        lastResetTime.putIfAbsent(uuid, System.currentTimeMillis());
    }

    public static void updateProgress(ServerPlayer player, String questId, int amount) {
        UUID uuid = player.getUUID();
        checkReset(player);

        if (completedQuests.get(uuid).contains(questId)) return;

        Map<String, Integer> progress = playerProgress.get(uuid);
        int current = progress.getOrDefault(questId, 0);
        int newProgress = current + amount;

        Quest quest = getQuestById(questId);
        if (quest == null) return;

        if (newProgress >= quest.target && current < quest.target) {
            completeQuest(player, quest);  // ← era completedQuests, corretto in completeQuest
            progress.put(questId, quest.target);
        } else {
            progress.put(questId, newProgress);
        }

        savePlayerQuests(player);
    }

    private static void completeQuest(ServerPlayer player, Quest quest) {
        UUID uuid = player.getUUID();
        completedQuests.get(uuid).add(quest.id);

        ChaosStats.addXP(player, quest.rewardXP, "Quest: " + quest.name);

        if (quest.rewardItem != null && !quest.rewardItem.isEmpty()) {
            if (!player.getInventory().add(quest.rewardItem.copy())) {  // ← era copyu(), corretto in copy()
                player.drop(quest.rewardItem.copy(), false);
            }
        }

        player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§a§l✨ QUEST COMPLETED! §r§a" + quest.name + "\n§7Rewards: §e+" + quest.rewardXP + " XP§7, §6+" + quest.rewardCoins + " coins§7, " + quest.rewardItem.getHoverName().getString()));
        System.out.println(player.getName().getString() + " completed quest: " + quest.name);

        ChaosEconomy.addCoins(player, quest.rewardCoins);
    }

    public static Quest getQuestById(String id) {
        for (Quest q : DAILY_QUESTS) {
            if (q.id.equals(id)) return q;
        }
        return null;
    }

    public static List<String> getQuestList(ServerPlayer player) {
        UUID uuid = player.getUUID();
        checkReset(player);

        List<String> result = new ArrayList<>();
        Map<String, Integer> progress = playerProgress.getOrDefault(uuid, new HashMap<>());
        Set<String> completed = completedQuests.getOrDefault(uuid, new HashSet<>());

        for (Quest quest : DAILY_QUESTS) {
            int current = progress.getOrDefault(quest.id, 0);
            String status;
            if (completed.contains(quest.id)) {
                status = "§a✔ COMPLETED";
            } else {
                status = "§e⏳ " + current + "/" + quest.target;
            }
            result.add("§7- " + quest.name + " §8(" + status + "§8)");
            result.add("§8  " + quest.description);
            result.add("§8  §7Rewards: §e+" + quest.rewardXP + " XP§7, §6+" + quest.rewardCoins + " coins");
            result.add("");
        }

        return result;
    }

    public static void savePlayerQuests(ServerPlayer player) {
        UUID uuid = player.getUUID();
        DataManager.QuestData questData = new DataManager.QuestData();
        questData.progress = playerProgress.getOrDefault(uuid, new HashMap<>());
        questData.completed = completedQuests.getOrDefault(uuid, new HashSet<>());
        questData.lastReset = lastResetTime.getOrDefault(uuid, System.currentTimeMillis());
        DataManager.saveQuestData(uuid, questData);
    }

    public static void loadPlayerQuests(ServerPlayer player) {
        UUID uuid = player.getUUID();
        DataManager.QuestData questData = DataManager.loadQuestData(uuid);  // ← era loadPlayerData, corretto in loadQuestData
        if (questData != null) {
            playerProgress.put(uuid, questData.progress);
            completedQuests.put(uuid, questData.completed);
            lastResetTime.put(uuid, questData.lastReset);
        }
    }

    ChaosEconomy.addCoins(player, quest.rewardCoins);
}
