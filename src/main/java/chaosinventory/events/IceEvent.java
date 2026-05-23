package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class IceEvent implements ChaosEvent {
    @Override public String getName() { return "Ice"; }
    @Override public int getWeight() { return 25; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.ICE, 1);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§b\uD83E\uDDCA Chaos donated you an ice block."));
    }
}
