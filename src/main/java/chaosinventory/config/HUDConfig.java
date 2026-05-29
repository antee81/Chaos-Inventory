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
import java.util.UUID;

public class HUDConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Map<UUID, HUDPreferences> preferences = new HashMap<>();
    private static Path configPath;

    public static HUDPreferences getPreferences(UUID uuid) {
        return preferences.computeIfAbsent(uuid, k -> new HUDPreferences());
    }

    public static final String DEFAULT_POSITION = "bottom_center";
    public static final int DEFAULT_COLOR = 0xFFFFFF;
    public static final int DEFAULT_OFFSET_X = 0;
    public static final int DEFAULT_OFFSET_Y = -75;

    private static final Map<String, Integer> COLOR_MAP = new HashMap<>();
    static {
        COLOR_MAP.put("white", 0xFFFFFF);
        COLOR_MAP.put("black", 0x000000);
        COLOR_MAP.put("red", 0xFF0000);
        COLOR_MAP.put("green", 0x00FF00);
        COLOR_MAP.put("blue", 0x0000FF);
        COLOR_MAP.put("yellow", 0xFFFF00);
        COLOR_MAP.put("cyan", 0x00FFFF);
        COLOR_MAP.put("purple", 0xFF00FF);
        COLOR_MAP.put("orange", 0xFFA500);
        COLOR_MAP.put("pink", 0xFFC0CB);
        COLOR_MAP.put("gray", 0x808080);
        COLOR_MAP.put("dark_red", 0x8B0000);
        COLOR_MAP.put("dark_green", 0x006400);
        COLOR_MAP.put("gold", 0xFFD700);
    }

    public static class HUDPreferences {
        public String position = DEFAULT_POSITION;
        public int color = DEFAULT_COLOR;
        public int offsetX = DEFAULT_OFFSET_X;
        public int offsetY = DEFAULT_OFFSET_Y;
    }

    public static void load() {
        try {
            configPath = FMLPaths.CONFIGDIR.get().resolve("chaosinventory_hud.json");

            if (Files.exists(configPath)) {
                String json = Files.readString(configPath);
                preferences = GSON.fromJson(json, Map.class);
                System.out.println("✅ HUDConfig loaded");
            } else {
                preferences = new HashMap<>();
                save();
                System.out.println("✅ HUDConfig created with default values.");
            }
        } catch (IOException e) {
            System.err.println("❌ Failed to load HUDConfig" + e.getMessage());
        }
    }

    public static void save() {
        try {
            if (configPath == null) {
                configPath = FMLPaths.CONFIGDIR.get().resolve("chaosinventory_hud.json");
            }
            String json = GSON.toJson(preferences);
            Files.writeString(configPath, json);
        } catch (IOException e) {
            System.err.println("❌ Failed to save HUDConfig" + e.getMessage());
        }
    }

    public static void setPosition(UUID uuid, String position) {
        getPreferences(uuid).position = position;
        save();
    }

    public static void setColor(UUID uuid, int color) {
        getPreferences(uuid).color = color;
        save();
    }

    public static boolean setColorByName(UUID uuid, String colorName) {
        String lowerName = colorName.toLowerCase();
        if (COLOR_MAP.containsKey(lowerName)) {
            setColor(uuid, COLOR_MAP.get(lowerName));
            return true;
        }
        return false;
    }

    public static void setOffset(UUID uuid, int offsetX, int offsetY) {
        HUDPreferences prefs = getPreferences(uuid);
        prefs.offsetX = offsetX;
        prefs.offsetY = offsetY;
        save();
    }

    public static void reset (UUID uuid) {
        HUDPreferences prefs = getPreferences(uuid);
        prefs.position = DEFAULT_POSITION;
        prefs.color = DEFAULT_COLOR;
        prefs.offsetX = DEFAULT_OFFSET_X;
        prefs.offsetY = DEFAULT_OFFSET_Y;
        save();
    }
}
