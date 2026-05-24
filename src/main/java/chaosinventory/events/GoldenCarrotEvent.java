package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GoldenCarrotEvent implements ChaosEvent {
    @Override public String getName() { return "Golden Carrot"; }
    @Override public int getWeight() { return 40; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.GOLDEN_CARROT, 5);
        if (InventoryHelper.tryAddItem(player, item, "Golden Apple")) {
            player.sendSystemMessage(Component.literal("§6\uD83E\uDD55 Chaos donated you 5 golden carrots!"));
        }
    }
}
