package chaosinventory.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import chaosinventory.ChaosInventory;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Path dataPath;

    private static Map<UUID, PlayerData> playerData = new HashMap<>();

    public static class PlayerData {
        public int xp = 0;
        public int level = 1;
        public int totalEvents = 0;
        public Map<String, Integer> eventCounts = new HashMap<>();
        public Map<String, Boolean> unlockedRewards = new HashMap<>();
    }

    public static void load() {
        try {
            dataPath = FMLPaths.CONFIGDIR.get().resolve("chaosinventory_data.json");

            if (Files.exists(dataPath)) {
                String json = Files.readString(dataPath);
                playerData = GSON.fromJson(json, Map.class);
                if (playerData == null) playerData = new HashMap<>();
                ChaosInventory.LOGGER.info("✅ Player data loaded from disk");
            } else {
                playerData = new HashMap<>();
                save();
                ChaosInventory.LOGGER.info("✅ New player data file created");
            }
        } catch (IOException e) {
            ChaosInventory.LOGGER.error("❌ Failed to load player data", e);
            playerData = new HashMap<>();
        }
    }

    public static void save() {
        try {
            if (dataPath == null) {
                dataPath = FMLPaths.CONFIGDIR.get().resolve("chaosinventory_data.json");
            }
            String json = GSON.toJson(playerData);
            Files.writeString(dataPath, json);
        } catch (IOException e) {
            ChaosInventory.LOGGER.error("❌ Failed to save player data", e);
        }
    }

    public static PlayerData getPlayerData(UUID uuid) {
        return playerData.computeIfAbsent(uuid, k -> new PlayerData());
    }

    public static void savePlayerData(UUID uuid) {
        save();
    }

    public static void removePlayer(UUID uuid) {
        playerData.remove(uuid);
        save();
    }
}
