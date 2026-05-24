package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ButtonEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Birch Button";
    }

    @Override
    public int getWeight() {
        return 40;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.BIRCH_BUTTON, 1);
        if (InventoryHelper.tryAddItem(player, item, "Birch Button")) {
            player.sendSystemMessage(Component.literal("§7\uD83D\uDD18 Chaos donated you 1 birch button."));
        }
    }
}
