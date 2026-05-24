package chaosinventory.utils;

import chaosinventory.ChaosInventory;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class InventoryHelper {
    public static boolean tryAddItem(ServerPlayer player, ItemStack item, String itemName) {
        if (hasFreeSlot(player)) {
            if (!player.getInventory().add(item)) {
                player.drop(item, false);
            }
            return true;
        } else {
            player.sendSystemMessage(Component.literal("§c§l❌ Chaos is sad! Your inventory is full, you cannot receive: §e" + itemName));
            return false;
        }
    }

    public static boolean hasFreeSlot(ServerPlayer player) {
        for (int i = 0; i < 36; i++) {
            if (player.getInventory().getItem(i).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public static int getFreeSlotsCount(ServerPlayer player) {
        int count = 0;
        for (int i = 0; i < 36; i++) {
            if (player.getInventory().getItem(i).isEmpty()) {
                count++;
            }
        }
        return count;
    }
}
