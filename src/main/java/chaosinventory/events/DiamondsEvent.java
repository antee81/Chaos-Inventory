package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DiamondsEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Pioggia di Diamanti";
    }

    @Override
    public int getWeight() {
        return 15; // RARO
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack diamonds = new ItemStack(Items.DIAMOND, 5);
        if (!player.getInventory().add(diamonds)) {
            player.drop(diamonds, false);
        }
        player.sendSystemMessage(Component.literal("§b\uD83D\uDC8E Chaos just donated you 5 diamonds!"));
    }
}

