package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PumpkinSeedsEvent implements ChaosEvent {
    @Override public String getName() { return "Pumpkin Seeds"; }
    @Override public int getWeight() { return 40; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.PUMPKIN_SEEDS, 10);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§a\uD83C\uDF83 Chaos donated you 10 pumpkin seed. Why 10?"));
    }
}
