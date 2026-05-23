package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CakeEvent implements ChaosEvent {
    @Override public String getName() { return "Cake"; }
    @Override public int getWeight() { return 25; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.CAKE, 1);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§d\uD83C\uDF82 Chaos donated you a cake!"));
    }
}
