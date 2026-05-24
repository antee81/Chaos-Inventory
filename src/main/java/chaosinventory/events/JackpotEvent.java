package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class JackpotEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "JACKPOT";
    }

    @Override
    public int getWeight() {
        return 2;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.DIAMOND_BLOCK, 3);
        if (InventoryHelper.tryAddItem(player, item, "3 Diamond Blocks")) {
            player.sendSystemMessage(Component.literal("§b§l🎰 JACKPOT! 3 DIAMOND BLOCKS"));
        }
    }
}
