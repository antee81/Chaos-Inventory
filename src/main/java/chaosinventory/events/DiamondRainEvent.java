package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DiamondRainEvent implements ChaosEvent {
    @Override public String getName() { return "Diamond Rain"; }
    @Override public int getWeight() { return 2; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.DIAMOND, 10);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§b\uD83D\uDC8E DIAMOND RAIN!! You received 10 diamonds!"));
    }
}
