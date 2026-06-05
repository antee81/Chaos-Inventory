package chaosinventory.leaderboard;

import chaosinventory.data.DataManager;
import net.minecraft.server.level.ServerPlayer;

import java.util.*;
import java.util.stream.Collectors;

public class LeaderboardManager {
    public static class PlayerStats {
        public String name;
        public int xp;
        public int level;
        public int coins;
        public int totalEvents;

        public PlayerStats(String name, int xp, int level, int coins, int totalEvents) {
            this.name = name;
            this.xp = xp;
            this.level = level;
            this.coins = coins;
            this.totalEvents = totalEvents;
        }
    }

    private static List<PlayerStats> getAllPlayersStats() {
        List<PlayerStats> stats = new ArrayList<>();

        for (Map.Entry<UUID, DataManager.PlayerData> entry : DataManager.getAllPlayerData().entrySet()) {
            UUID uuid = entry.getKey();
            DataManager.PlayerData data = entry.getValue();

            String name = DataManager.getPlayerName(uuid);
            if (name == null) name = "Unknown";

            stats.add(new PlayerStats(name, data.xp, data.level, data.coins, data.totalEvents));
        }

        stats.sort((a, b) -> Integer.compare(b.xp, a.xp));

        return stats;
    }

    public static List<String> getLeaderboard(ServerPlayer requestor) {
        List<String> result = new ArrayList<>();
        List<PlayerStats> stats = getAllPlayersStats();

        if (stats.isEmpty()) {
            result.add("§7No players found!");
            return result;
        }

        result.add("§6§l\uD83C\uDFC6 LEADERBOARD");
        result.add("§7━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        result.add("");

        int rank = 1;
        for (PlayerStats ps : stats) {
            if (rank > 10) break;

            String rankColor = getRankColor(rank);
            result.add(rankColor + "#" + rank + " §7" + ps.name);
            result.add("§8  §7XP: §e" + ps.xp + " §7| Level: §a" + ps.level);
            result.add("§8  §7Coins: §6" + ps.coins + " §7| Events: §c" + ps.totalEvents);
            result.add("");

            rank++;
        }

        PlayerStats requestorStats = stats.stream()
                .filter(s -> s.name.equals(requestor.getName().getString()))
                .findFirst()
                .orElse(null);

        if (requestorStats != null) {
            int playerRank = stats.indexOf(requestorStats) + 1;
            if (playerRank > 10) {
                result.add("§7━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                result.add("§7Your position:");
                result.add("§8#" + playerRank + " §7" + requestorStats.name);
                result.add("§8  §7XP: §e" +  requestorStats.xp + " §7| Level: §a" + requestorStats.level);
                result.add("§8  §7Coins: §6" + requestorStats.coins + " §7| Events: §c" + requestorStats.totalEvents);
            }
        }

        return result;
    }

    private static String getRankColor(int rank) {
        switch (rank) {
            case 1: return "§6§l";
            case 2: return "§7§l";
            case 3: return "§6§l";
            default: return "§e";
        }
    }
}
