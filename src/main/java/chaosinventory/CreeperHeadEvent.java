package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CreeperHeadEvent implements ChaosEvent {
    @Override public String getName() { return "Creeper's Head"; }
    @Override public int getWeight() { return 15; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.CREEPER_HEAD, 1);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§2\uD83D\uDC80 Chaos donated you a creeper's head.. it seems disturbing.."));
    }
}
