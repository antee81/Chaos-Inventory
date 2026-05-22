package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DirtEvent implements ChaosEvent {
    @Override public String getName() { return "Dirt"; }
    @Override public int getWeight() { return 40; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.DIRT, 16);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§8⛰ Chaos donated you 16 blocks of dirt.. thank you?"));
    }
}
