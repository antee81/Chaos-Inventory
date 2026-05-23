package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;

public class InfinityBowEvent implements ChaosEvent {
    @Override public String getName() { return "Infinity Bow"; }
    @Override public int getWeight() { return 15; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack bow = new ItemStack(Items.BOW, 1);
        bow.enchant(Enchantments.INFINITY_ARROWS, 1);
        if (!player.getInventory().add(bow)) player.drop(bow, false);
        player.sendSystemMessage(Component.literal("§a\uD83C\uDFF9 Chaos just donated a Bow with Infinity!! Congrats i will be very grateful for this if I was you."));
    }
}
