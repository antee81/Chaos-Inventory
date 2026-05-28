package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemSwapperEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Item_Swapper";
    }

    @Override
    public int getWeight() {
        return 8;
    }

    @Override
    public boolean isMultiplayerOnly() {
        return true;
    }

    @Override
    public void execute(ServerPlayer player) {
        ServerPlayer target = null;
        double closestDistance = 20.0;

        for (ServerPlayer other : player.serverLevel().getServer().getPlayerList().getPlayers()) {
            if (other != player && other.distanceTo(player) <= closestDistance) {
                target = other;
                closestDistance = other.distanceTo(player);
            }
        }

        if (target == null) {
            player.sendSystemMessage(Component.literal("§7⚠\uFE0F No nearby players to swap inventory!"));
            return;
        }

        Inventory inv1 = player.getInventory();
        Inventory inv2 = target.getInventory();

        List<ItemStack> tempInv = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            tempInv.add(inv1.getItem(i));
        }

        for (int i = 0; i < 36; i++) {
            inv1.setItem(i, inv2.getItem(i));

        }

        for (int i = 0; i < 36; i++) {
            inv2.setItem(i, tempInv.get(i));
        }

        player.sendSystemMessage(Component.literal("§5\uD83D\uDD04 Chaos just swapped your inventory with §e" + target.getName().getString()));
        target.sendSystemMessage(Component.literal("§5\uD83D\uDD04 Chaos just swapped your inventory with §e" + player.getName().getString()));
    }
}
