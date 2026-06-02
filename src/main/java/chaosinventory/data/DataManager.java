package chaosinventory.data;

import chaosinventory.ChaosInventory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.imageio.plugins.tiff.TIFFDirectory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DataManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Path dataFolder;
    private static final Map<UUID, PlayerData> playerDataCache = new HashMap<>();

    public static class PlayerData {
        public int xp = 0;
        public int level = 1;
        public int totalEvents = 0;
        public int coins = 0;
        public int diamondEvents = 0;
        public int tntEvents = 0;
        public int teleportEvents = 0;
        public Map<String, Integer> eventCounts = new HashMap<>();
        public Map<String, Boolean> unlockedRewards = new HashMap<>();

        public PlayerData() {}
    }

    public static void init() {
        try {
            dataFolder = Path.of("chaos_data");
            if (!Files.exists(dataFolder)) {
                Files.createDirectories(dataFolder);
                System.out.println("Created chaos_data folder");
            }
        } catch (IOException e) {
            System.err.println("Failed to create chaos_data folder" + e.getMessage());
        }
    }

    public static void savePlayerData(UUID uuid, PlayerData data) {
        if (dataFolder == null) init();
        try {
            Path playerFile = dataFolder.resolve(uuid.toString() + ".json");
            String json = GSON.toJson(data);
            Files.writeString(playerFile, json);
            playerDataCache.put(uuid, data);
        } catch (IOException e) {
            System.err.println("Failed to save data for player: " + uuid + e.getMessage());
        }
    }

    public static PlayerData loadPlayerData(UUID uuid) {
        if (dataFolder == null) init();

        if (playerDataCache.containsKey(uuid)) {
            return playerDataCache.get(uuid);
        }

        try {
            Path playerFile = dataFolder.resolve(uuid.toString() + ".json");
            if (Files.exists(playerFile)) {
                String json = Files.readString(playerFile);
                PlayerData data = GSON.fromJson(json, PlayerData.class);
                playerDataCache.put(uuid, data);
                System.out.println("Loaded data for player: " + uuid);
                return data;
            }
        } catch (IOException e) {
            System.err.println("Failed to load data for player: " + uuid + e.getMessage());
        }
        PlayerData newData = new PlayerData();
        playerDataCache.put(uuid, newData);
        return newData;
    }

    public static void saveAll() {
        for (Map.Entry<UUID, PlayerData> entry : playerDataCache.entrySet()) {
            savePlayerData(entry.getKey(), entry.getValue());
        }
        System.out.println("Saved all player data");
    }

    public static void removeFromCache(UUID uuid) {
        if (playerDataCache.containsKey(uuid)) {
            savePlayerData(uuid, playerDataCache.get(uuid));
            playerDataCache.remove(uuid);
        }
    }

    public static void updatePlayerData(UUID uuid, PlayerData data) {
        playerDataCache.put(uuid, data);
        savePlayerData(uuid, data);
    }

    public static class QuestData {
        public Map<String, Integer> progress = new HashMap<>();
        public Set<String> completed = new HashSet<>();
        public long lastReset = System.currentTimeMillis();
    }

    public static void saveQuestData(UUID uuid, QuestData data) {
        try {
            Path playerFile = dataFolder.resolve(uuid + "_quests.json");
            String json = GSON.toJson(data);
            Files.writeString(playerFile, json);
        } catch (IOException e) {
            System.err.println("Failed to save quest data for: " + uuid);
        }
    }

    public static QuestData loadQuestData(UUID uuid) {
        try {
            Path playerFile = dataFolder.resolve(uuid + "_quests.json");
            if (Files.exists(playerFile)) {
                String json = Files.readString(playerFile);
                return GSON.fromJson(json, QuestData.class);
            }
        } catch (IOException e) {
            System.err.println("Failed to load quest data for: " + uuid);
        }
        return new QuestData();
    }

    public int coins = 0;

    public static int getCoins(UUID uuid) {
        PlayerData data = loadPlayerData(uuid);
        return data.coins;
    }

    public static void setCoins(UUID uuid, int amount) {
        PlayerData data = loadPlayerData(uuid);
        data.coins = amount;
        updatePlayerData(uuid, data);
    }

    public static int getDiamondEventCount(UUID uuid) {
        return loadPlayerData(uuid).diamondEvents;
    }

    public static void setDiamondEventCount(UUID uuid, int count) {
        PlayerData data = loadPlayerData(uuid);
        data.diamondEvents = count;
        updatePlayerData(uuid, data);
    }

    public static int getTNTEventCount(UUID uuid) {
        return loadPlayerData(uuid).tntEvents;
    }

    public static void setTNTEventCount(UUID uuid, int count) {
        PlayerData data = loadPlayerData(uuid);
        data.tntEvents = count;
        updatePlayerData(uuid, data);
    }

    public static int getTeleportCount(UUID uuid) {
        return loadPlayerData(uuid).teleportEvents;
    }

    public static void setTeleportCount(UUID uuid, int count) {
        PlayerData data = loadPlayerData(uuid);
        data.teleportEvents = count;
        updatePlayerData(uuid, data);
    }

    public static void saveAchievements(UUID uuid, Set<String> achievements) {
        if (dataFolder == null) init();
        try {
            Path achFile = dataFolder.resolve(uuid.toString() + "_achievements.json");
            String json = GSON.toJson(achievements);
            Files.writeString(achFile, json);
        } catch (IOException e) {
            System.err.println("Failed to save achievements for: " + uuid);
        }
    }

    public static Set<String> loadAchievements(UUID uuid) {
        if (dataFolder == null) init();
        try {
            Path achFile = dataFolder.resolve(uuid.toString() + "_achievements.json");

            if (Files.exists(achFile)) {
                String json = Files.readString(achFile);
                List<String> list = GSON.fromJson(json, new com.google.gson.reflect.TypeToken<List<String>>(){}.getType());
                return new HashSet<>();
            }
        } catch (IOException e) {
            System.err.println("Failed to load achievements for: " + uuid);
        }
        return new HashSet<>();
    }
}
