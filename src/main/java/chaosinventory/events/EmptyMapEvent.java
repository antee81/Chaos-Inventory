package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EmptyMapEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Empty Map";
    }

    @Override
    public int getWeight() {
        return 25;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.MAP, 1);
        if (InventoryHelper.tryAddItem(player, item, "Tropical Fish")) {
            player.sendSystemMessage(Component.literal("§e\uD83D\uDDFA Chaos just donated you an empty map. Completely useless."));
        }
    }
}
