package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InventoryVoidEvent implements ChaosEvent {
    private static final Random RANDOM = new Random();

    @Override
    public String getName() {
        return "Inventory_Void";
    }

    @Override
    public int getWeight() {
        return 8;
    }

    @Override
    public void execute(ServerPlayer player) {
        Inventory inv = player.getInventory();
        List<Integer> validSlots = new ArrayList<>();

        for (int i = 0; i < 36; i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && !isProtected(stack, player, i)) {
                validSlots.add(i);
            }
        }

        if (validSlots.isEmpty()) {
            player.sendSystemMessage(Component.literal("§7✨ The Void is protecting you today! No items lost"));
            return;
        }

        int slotsToRemove = Math.min(5, validSlots.size());
        List<Integer> removedSlots = new ArrayList<>();

        for (int j = 0; j < slotsToRemove; j++) {
            int randomIndex = RANDOM.nextInt(validSlots.size());
            int slot = validSlots.get(randomIndex);
            removedSlots.add(slot);
            validSlots.remove(randomIndex);
        }

        StringBuilder lostItems = new StringBuilder();
        for (int slot : removedSlots) {
            ItemStack stack = inv.getItem(slot);
            if (!stack.isEmpty()) {
                if (lostItems.length() > 0) lostItems.append(", ");
                lostItems.append(stack.getHoverName().getString());
                inv.setItem(slot, ItemStack.EMPTY);
            }
        }

        player.sendSystemMessage(Component.literal("§5🌌 The void has swallowed: §c" + lostItems.toString()));
        player.sendSystemMessage(Component.literal("§8§o(Don't worry, all your important items are safe, for now!)"));
    }

    private boolean isProtected(ItemStack stack, ServerPlayer player, int slot) {
        // Totem e Elytra
        if (stack.getItem() == Items.TOTEM_OF_UNDYING) return true;
        if (stack.getItem() == Items.ELYTRA) return true;

        // Netherite items
        if (stack.getItem().toString().toLowerCase().contains("netherite")) return true;

        // Armor indossata (slot 36-39)
        if (slot >= 36 && slot <= 39) return true;

        // Item in mano
        if (player.getMainHandItem() == stack || player.getOffhandItem() == stack) return true;

        // Oggetti incantati
        if (EnchantmentHelper.getEnchantments(stack).size() > 0) return true;  // ← CORRETTO

        return false;
    }
}