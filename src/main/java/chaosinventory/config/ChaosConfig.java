package chaosinventory.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import chaosinventory.ChaosInventory;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ChaosConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ConfigData config = new ConfigData();
    private static Path configPath;

    public static void load() {
        try {
            configPath = FMLPaths.CONFIGDIR.get().resolve("chaosinventory.json");

            if (Files.exists(configPath)) {
                String json = Files.readString(configPath);
                config = GSON.fromJson(json, ConfigData.class);
                ChaosInventory.LOGGER.info("✅ ChaosConfig loaded from file");
            } else {
                initDefaultEventSettings();
                save();
                ChaosInventory.LOGGER.info("✅ ChaosConfig created with default values");
            }
        } catch (IOException e) {
            ChaosInventory.LOGGER.error("❌ Failed to load ChaosConfig", e);
        }
    }

    public static void save() {
        try {
            if (configPath == null) {
                configPath = FMLPaths.CONFIGDIR.get().resolve("chaosinventory.json");
            }
            String json = GSON.toJson(config);
            Files.writeString(configPath, json);
            ChaosInventory.LOGGER.info("\uD83D\uDCBE ChaosConfig saved");
        } catch (IOException e) {
            ChaosInventory.LOGGER.error("❌ Failed to save ChaosConfig", e);
        }
    }

    public static int getChaosDurationTicks() {
        return config.chaosDurationSeconds * 20;
    }

    public static int getChaosDurationSeconds() {
        return config.chaosDurationSeconds;
    }

    public static boolean isEventEnabled(String eventName) {
        return config.enabledEvents.getOrDefault(eventName, true);
    }

    public static int getEventWeight(String eventName, int defaultWeight) {
        return config.eventWeights.getOrDefault(eventName, defaultWeight);
    }

    public static boolean isMultiplayerOnly() {
        return config.multiplayerOnly;
    }

    public static void setChaosDurationSeconds(int seconds) {
        config.chaosDurationSeconds = seconds;
        save();
    }

    public static void setEventEnabled(String eventName, boolean enabled) {
        config.enabledEvents.put(eventName, enabled);
        save();
    }

    public static void setEventWeight(String eventName, int weight) {
        config.eventWeights.put(eventName, weight);
        save();
    }

    private static class ConfigData {
        int chaosDurationSeconds = 30 * 60;
        boolean multiplayerOnly = false;
        Map<String, Boolean> enabledEvents = new HashMap<>();
        Map<String, Integer> eventWeights = new HashMap<>();
        String timerColor = "WHITE";
        Map<String, String> playerColors = new HashMap<>();
    }

    public static String getPlayerTimerColor(String uuid) {
        return config.playerColors.getOrDefault(uuid, "WHITE");
    }

    public static void setPlayerTimerColor(String uuid, String color) {
        config.playerColors.put(uuid, color);
        save();
    }

    public static String getTimerColor() {
        return config.timerColor;
    }

    public static void setTimerColor(String color) {
        config.timerColor = color;
        save();
    }

    public static final Map<String, Integer> COLOR_CODES = new HashMap<>();
    static {
        COLOR_CODES.put("BLACK", 0x000000);
        COLOR_CODES.put("DARK_BLUE", 0x0000AA);
        COLOR_CODES.put("DARK_GREEN", 0x00AA00);
        COLOR_CODES.put("DARK_AQUA", 0x00AAAA);
        COLOR_CODES.put("DARK_RED", 0xAA0000);
        COLOR_CODES.put("DARK_PURPLE", 0xAA00AA);
        COLOR_CODES.put("GOLD", 0xFFAA00);
        COLOR_CODES.put("GRAY", 0xAAAAAA);
        COLOR_CODES.put("DARK_GRAY", 0x555555);
        COLOR_CODES.put("BLUE", 0x5555FF);
        COLOR_CODES.put("GREEN", 0x55FF55);
        COLOR_CODES.put("AQUA", 0x55FFFF);
        COLOR_CODES.put("RED", 0xFF5555);
        COLOR_CODES.put("LIGHT_PURPLE", 0xFF55FF);
        COLOR_CODES.put("YELLOW", 0xFFFF55);
        COLOR_CODES.put("WHITE", 0xFFFFFF);
    }

    private static void initDefaultEventSettings() {
        config.enabledEvents.put("Diamonds", true);
        config.eventWeights.put("Diamonds", 15);

        config.enabledEvents.put("Golden Apple", true);
        config.eventWeights.put("Golden Apple", 25);

        config.enabledEvents.put("Golden Carrot", true);
        config.eventWeights.put("Golden Carrot", 40);

        config.enabledEvents.put("Ender Pearl", true);
        config.eventWeights.put("Ender Pearl", 25);

        config.enabledEvents.put("Shield", true);
        config.eventWeights.put("Shield", 40);

        config.enabledEvents.put("Spectral Arrows", true);
        config.eventWeights.put("Spectral Arrows", 40);

        config.enabledEvents.put("Diamond Block", true);
        config.eventWeights.put("Diamond Block", 15);

        config.enabledEvents.put("Strength Potion", true);
        config.eventWeights.put("Strength Potion", 25);

        config.enabledEvents.put("Invisibility Potion", true);
        config.eventWeights.put("Invisibility Potion", 25);

        config.enabledEvents.put("Netherite Sword", true);
        config.eventWeights.put("Netherite Sword", 5);

        config.enabledEvents.put("Totem of Undying", true);
        config.eventWeights.put("Totem of Undying", 5);

        config.enabledEvents.put("Elytra", true);
        config.eventWeights.put("Elytra", 2);

        config.enabledEvents.put("Infinity Bow", true);
        config.eventWeights.put("Infinity Bow", 15);

        config.enabledEvents.put("Efficiency Pickaxe", true);
        config.eventWeights.put("Efficiency Pickaxe", 15);

        config.enabledEvents.put("Complete Netherite Armor", true);
        config.eventWeights.put("Complete Netherite Armor", 2);

        config.enabledEvents.put("Poisonous Potato", true);
        config.eventWeights.put("Poisonous Potato", 40);

        config.enabledEvents.put("Dirt", true);
        config.eventWeights.put("Dirt", 40);

        config.enabledEvents.put("Birch Button", true);
        config.eventWeights.put("Birch Button", 40);

        config.enabledEvents.put("Pumpkin Seeds", true);
        config.eventWeights.put("Pumpkin Seeds", 40);

        config.enabledEvents.put("Tropical Fish", true);
        config.eventWeights.put("Tropical Fish", 40);

        config.enabledEvents.put("Empty Map", true);
        config.eventWeights.put("Empty Map", 25);

        config.enabledEvents.put("Boat", true);
        config.eventWeights.put("Boat", 25);

        config.enabledEvents.put("Saddle", true);
        config.eventWeights.put("Saddle", 25);

        config.enabledEvents.put("Ice", true);
        config.eventWeights.put("Ice", 25);

        config.enabledEvents.put("Cake", true);
        config.eventWeights.put("Cake", 25);

        config.enabledEvents.put("Broken Compass", true);
        config.eventWeights.put("Broken Compass", 25);

        config.enabledEvents.put("Jukebox", true);
        config.eventWeights.put("Jukebox", 15);

        config.enabledEvents.put("Creeper Head", true);
        config.eventWeights.put("Creeper Head", 15);

        config.enabledEvents.put("Book RUN", true);
        config.eventWeights.put("Book RUN", 15);

        config.enabledEvents.put("Book Behind You", true);
        config.eventWeights.put("Book Behind You", 15);

        config.enabledEvents.put("TNT Donated", true);
        config.eventWeights.put("TNT Donated", 25);

        config.enabledEvents.put("Lava Bucket", true);
        config.eventWeights.put("Lava Bucket", 5);

        config.enabledEvents.put("Hunger III", true);
        config.eventWeights.put("Hunger III", 40);

        config.enabledEvents.put("Slowness IV", true);
        config.eventWeights.put("Slowness IV", 40);

        config.enabledEvents.put("Blindness", true);
        config.eventWeights.put("Blindness", 25);

        config.enabledEvents.put("Damage Potion", true);
        config.eventWeights.put("Damage Potion", 15);

        config.enabledEvents.put("Random Teleport", true);
        config.eventWeights.put("Random Teleport", 5);

        config.enabledEvents.put("Inventario Mescolato", true);
        config.eventWeights.put("Inventario Mescolato", 25);

        config.enabledEvents.put("Hotbar Shuffled", true);
        config.eventWeights.put("Hotbar Shuffled", 40);

        config.enabledEvents.put("Zombie Egg x10", true);
        config.eventWeights.put("Zombie Egg x10", 25);

        config.enabledEvents.put("Enderman Eggs x3", true);
        config.eventWeights.put("Enderman Eggs x3", 15);

        config.enabledEvents.put("Phantom Eggs x5", true);
        config.eventWeights.put("Phantom Eggs x5", 15);

        config.enabledEvents.put("Creeper Eggs x3", true);
        config.eventWeights.put("Creeper Eggs x3", 15);

        config.enabledEvents.put("Vex Eggs x3", true);
        config.eventWeights.put("Vex Eggs x3", 15);

        config.enabledEvents.put("Wither Skeleton Eggs", true);
        config.eventWeights.put("Wither Skeleton Eggs", 5);

        config.enabledEvents.put("Diamond Rain", true);
        config.eventWeights.put("Diamond Rain", 2);

        config.enabledEvents.put("JACKPOT", true);
        config.eventWeights.put("JACKPOT", 2);

        config.enabledEvents.put("Cow Rain", true);
        config.eventWeights.put("Cow Rain", 15);

        config.enabledEvents.put("Random Explosion", true);
        config.eventWeights.put("Random Explosion", 15);

        config.enabledEvents.put("Earthquake", true);
        config.eventWeights.put("Earthquake", 5);

        config.enabledEvents.put("Gravity Flip", true);
        config.eventWeights.put("Gravity Flip", 10);

        config.enabledEvents.put("Item Swapper", true);
        config.eventWeights.put("Item Swapper", 8);

        config.enabledEvents.put("Time Warp", true);
        config.eventWeights.put("Time Warp", 10);

        config.enabledEvents.put("Chat Spam", true);
        config.eventWeights.put("Chat Spam", 20);

        config.enabledEvents.put("Fake Death", true);
        config.eventWeights.put("Fake Death", 15);

        config.enabledEvents.put("Invert Controls", true);
        config.eventWeights.put("Invert Controls", 12);

        config.enabledEvents.put("Random Potion", true);
        config.eventWeights.put("Random Potion", 18);

        config.enabledEvents.put("Villager Rain", true);
        config.eventWeights.put("Villager Rain", 6);

        config.enabledEvents.put("Lightning Strike", true);
        config.eventWeights.put("Lightning Strike", 7);

        config.enabledEvents.put("Inventory Void", true);
        config.eventWeights.put("Inventory Void", 8);
    }
}
