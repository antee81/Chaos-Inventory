package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DiamondBlockEvent implements ChaosEvent {
    @Override public String getName() { return "Diamond Block";}
    @Override public int getWeight() { return 15; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.DIAMOND_BLOCK, 1);
        if (InventoryHelper.tryAddItem(player, item, "Diamond Block")) {
            player.sendSystemMessage(Component.literal("§b\uD83D\uDC8E Chaos donated you a Diamond Block!!"));
        }
    }
}
