package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SpectralArrowsEvent implements ChaosEvent {
    @Override public String getName() { return "Spectral Arrows"; }
    @Override public int getWeight() { return 40; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.SPECTRAL_ARROW, 8);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§f\uD83C\uDFF9 Chaos donated you 8 spectral arrows!"));
    }
}
