package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EndermanEggsEvent implements ChaosEvent {
    @Override public String getName() { return "Enderman Eggs x2"; }
    @Override public int getWeight() { return 25; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.ENDERMAN_SPAWN_EGG, 2);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§5\uD83D\uDC79 Chaos just donated you 2 Enderman Eggs!"));
    }
}
