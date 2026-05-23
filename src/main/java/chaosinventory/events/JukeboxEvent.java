package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class JukeboxEvent implements ChaosEvent {
    @Override public String getName() { return "Jukebox"; }
    @Override public int getWeight() { return 15; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.JUKEBOX, 1);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§6\uD83D\uDCBF Chaos just donated you a Jukebox. But without a disk..."));
    }
}
