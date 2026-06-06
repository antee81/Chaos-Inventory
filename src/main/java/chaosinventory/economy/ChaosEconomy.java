package chaosinventory.economy;

import chaosinventory.ChaosInventory;
import chaosinventory.achievements.AchievementManager;
import chaosinventory.data.DataManager;
import chaosinventory.utils.LanguageManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class ChaosEconomy {

    public static final List<ShopItem> SHOP_ITEMS = new ArrayList<>();

    public static class ShopItem {
        public String id;
        public String name;
        public int price;
        public String itemId;
        public String description;

        public ShopItem(String id, String name, int price, String itemId, String description) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.itemId = itemId;
            this.description = description;
        }
    }

    static {
        SHOP_ITEMS.add(new ShopItem("diamond", "§bDiamond", 50, "minecraft:diamond", "§7A precious diamond"));
        SHOP_ITEMS.add(new ShopItem("diamond_block", "§bDiamond Block", 450, "minecraft:diamond_block", "§7A block of diamonds"));
        SHOP_ITEMS.add(new ShopItem("golden_apple", "§6Golden Apple", 75, "minecraft:golden_apple", "§7Restores 4 hearts"));
        SHOP_ITEMS.add(new ShopItem("enchanted_gapple", "§dEnchanted Golden Apple", 300, "minecraft:enchanted_golden_apple", "§7Powerful apple"));
        SHOP_ITEMS.add(new ShopItem("elytra", "§dElytra", 1000, "minecraft:elytra", "§7Glide through the air"));
        SHOP_ITEMS.add(new ShopItem("totem", "§eTotem of Undying", 500, "minecraft:totem_of_undying", "§7Save you from death"));
        SHOP_ITEMS.add(new ShopItem("xp_bottle", "§aXP Bottle", 30, "minecraft:experience_bottle", "§7Gain 10-30 XP"));
        SHOP_ITEMS.add(new ShopItem("speed_boost", "§bSpeed Boost", 100, "minecraft:sugar", "§7Speed II for 2 minutes"));
        SHOP_ITEMS.add(new ShopItem("chaos_token", "§5Chaos Token", 5000, "minecraft:nether_star", "§7Redeem for a legendary event"));
        SHOP_ITEMS.add(new ShopItem("timer_freeze", "§3Timer Freeze", 200, "minecraft:clock", "§7Freeze chaos timer for 5 minutes"));
        SHOP_ITEMS.add(new ShopItem("double_xp", "§eDouble XP", 300, "minecraft:golden_carrot", "§7Double XP for 30 minutes"));
        SHOP_ITEMS.add(new ShopItem("shield", "§7Shield", 40, "minecraft:shield", "§7Block incoming attacks"));
    }

    private static ItemStack createItemFromItemId(String itemId, int amount) {
        net.minecraft.resources.ResourceLocation location = new net.minecraft.resources.ResourceLocation(itemId);
        net.minecraft.world.item.Item item = net.minecraftforge.registries.ForgeRegistries.ITEMS.getValue(location);
        if (item != null && item != Items.AIR) {
            return new ItemStack(item, amount);
        }
        return null;
    }

    public static int getCoins(ServerPlayer player) {
        return DataManager.getCoins(player.getUUID());
    }

    public static void addCoins(ServerPlayer player, int amount) {
        if (amount <= 0) return;
        UUID uuid = player.getUUID();
        int current = DataManager.getCoins(uuid);
        DataManager.setCoins(uuid, current + amount);

        player.sendSystemMessage(Component.literal(LanguageManager.get("chaos.coins.gained", amount, current + amount)));

        AchievementManager.checkAndUnlock(player, "coins", current + amount);
        System.out.println(player.getName().getString() + " gained " + amount + " coins");
    }

    public static boolean removeCoins(ServerPlayer player, int amount) {
        if (amount <= 0) return true;
        UUID uuid = player.getUUID();
        int current = DataManager.getCoins(uuid);
        if (current < amount) {
            player.sendSystemMessage(Component.literal("§c❌ You don't have enough Chaos Coins! Need §e" + amount + "§c, you have §e" + current));
            return false;
        }
        DataManager.setCoins(uuid, current - amount);
        player.sendSystemMessage(Component.literal(LanguageManager.get("chaos.coins.spent", amount, current - amount)));
        return true;
    }

    public static int getCoins(UUID uuid) {
        return DataManager.getCoins(uuid);
    }

    public static boolean removeCoins(UUID uuid, int amount) {
        if (amount <= 0) return true;
        int current = DataManager.getCoins(uuid);
        if (current < amount) return false;
        DataManager.setCoins(uuid, current - amount);
        return true;
    }

    public static boolean buyItem(ServerPlayer player, String id, int amount) {
        ShopItem shopItem = getShopItem(id);
        if (shopItem == null) {
            player.sendSystemMessage(Component.literal("§c❌ Invalid item!"));
            return false;
        }

        int totalPrice = shopItem.price * amount;
        if (!removeCoins(player, totalPrice)) return false;

        ItemStack itemStack = createItemFromItemId(shopItem.itemId, amount);
        if (itemStack == null) {
            player.sendSystemMessage(Component.literal("§c❌ Error creating item!"));
            return false;
        }

        if (!player.getInventory().add(itemStack)) {
            player.drop(itemStack, false);
        }

        player.sendSystemMessage(Component.literal(LanguageManager.get("chaos.shop.bought", shopItem.name, totalPrice)));
        return true;
    }

    public static ShopItem getShopItem(String id) {
        for (ShopItem item : SHOP_ITEMS) {
            if (item.id.equalsIgnoreCase(id)) return item;
        }
        return null;
    }

    public static List<ShopItem> getShopItems() {
        return SHOP_ITEMS;
    }
}
