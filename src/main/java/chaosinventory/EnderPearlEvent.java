package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.core.jmx.Server;

import javax.swing.*;

public class EnderPearlEvent implements ChaosEvent {
    @Override public String getName() { return "Ender Pearl"; }
    @Override public int getWeight() { return 25; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.ENDER_PEARL, 1);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§5\uD83D\uDFE3 Chaos donated you 1 Ender Pearl!"));
    }
}
