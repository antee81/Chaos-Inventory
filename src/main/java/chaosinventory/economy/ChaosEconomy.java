package chaosinventory.economy;

import chaosinventory.ChaosInventory;
import chaosinventory.data.DataManager;
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
        public ItemStack item;
        public String description;

        public ShopItem(String id, String name, int price, ItemStack item, String description) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.item = item;
            this.description = description;
        }
    }

    static {
        SHOP_ITEMS.add(new ShopItem("diamond", "§bDiamond", 50, new ItemStack(Items.DIAMOND, 1), "§7A precious diamond"));
        SHOP_ITEMS.add(new ShopItem("diamond_block", "§bDiamond Block", 450, new ItemStack(Items.DIAMOND_BLOCK, 1), "§7A block of diamonds"));
        SHOP_ITEMS.add(new ShopItem("golden_apple", "§6Golden Apple", 75, new ItemStack(Items.GOLDEN_APPLE, 1), "§7Restores 4 hearts"));
        SHOP_ITEMS.add(new ShopItem("enchanted_gapple", "§dEnchanted Golden Apple", 300, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1), "§Powerful apple"));
        SHOP_ITEMS.add(new ShopItem("elytra", "§dElytra", 1000, new ItemStack(Items.ELYTRA, 1), "§Glide through the air"));
        SHOP_ITEMS.add(new ShopItem("totem", "§eTotem of Undying", 500, new ItemStack(Items.TOTEM_OF_UNDYING, 1), "§7Save you from death"));

        SHOP_ITEMS.add(new ShopItem("xp_bottle", "§aXP Bottle", 30, new ItemStack(Items.EXPERIENCE_BOTTLE, 3), "§7Gain 10-30 XP"));

        SHOP_ITEMS.add(new ShopItem("speed_boost", "§bSpeed Boost", 100, new ItemStack(Items.SUGAR, 1), "§7Speed II for 2 minutes"));
        SHOP_ITEMS.add(new ShopItem("chaos_token", "§5Chaos Token", 5000, new ItemStack(Items.NETHER_STAR, 1), "§7Redeem for a legendary event"));
        SHOP_ITEMS.add(new ShopItem("timer_freeze", "§3Timer Freeze", 200, new ItemStack(Items.CLOCK, 1), "§7Freeze chaos timer for 5 minutes"));
        SHOP_ITEMS.add(new ShopItem("double_xp", "§eDouble XP", 300, new ItemStack(Items.GOLDEN_CARROT, 1), "§7Double XP for 30 minutes"));
    }

    public static int getCoins(ServerPlayer player) {
        return DataManager.getCoins(player.getUUID());
    }

    public static void addCoins(ServerPlayer player, int amount) {
        if (amount <= 0) return;
        UUID uuid = player.getUUID();
        int current = DataManager.getCoins(uuid);
        DataManager.setCoins(uuid, current + amount);
        player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§6\uD83D\uDCB0 +" + amount + " Chaos Coins! §7Total: §e" + (current + amount)));
        ChaosInventory.LOGGER.info(player.getName().getString() + " gained " + amount + " coins");
    }

    public static boolean removeCoins(ServerPlayer player, int amount) {
        if (amount <= 0) return true;
        UUID uuid = player.getUUID();
        int current = DataManager.getCoins(uuid);
        if (current < amount) {
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§c❌ You don't have enough Chaos Coins! Need §e" + amount + "§c, you have §e" + current));
            return false;
        }
        DataManager.setCoins(uuid, current - amount);
        player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§6\uD83D\uDCB0 -" + amount + " Chaos Coins! §7Remaining: §e" + (current - amount)));
        return true;
    }

    public static boolean buyItem(ServerPlayer player, String itemId, int amount) {
        ShopItem shopItem = getShopItem(itemId);
        if (shopItem == null) {
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§c❌ Invalid item!"));
            return false;
        }

        int totalPrice = shopItem.price * amount;
        if (!removeCoins(player, totalPrice)) return false;

        ItemStack itemStack = shopItem.item.copy();
        ItemStack.setCount(amount);

        if (!player.getInventory().add(ItemStack)) {
            player.drop(itemStack, false);
        }

        player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§a✅ You bought §e" + amount + "x " + shopItem.name + "§a for §6" + totalPrice + " coins§a!"));
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
