package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class JukeboxEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Jukebox";
    }

    @Override
    public int getWeight() {
        return 15;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.JUKEBOX, 1);
        if (InventoryHelper.tryAddItem(player, item, "Jukebox")) {
            player.sendSystemMessage(Component.literal("§6\uD83D\uDCBF Chaos just donated you a Jukebox. But without a disk..."));
        }
    }
}
