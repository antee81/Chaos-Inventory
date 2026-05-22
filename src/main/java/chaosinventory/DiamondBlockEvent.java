package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.core.jmx.Server;

public class DiamondBlockEvent implements ChaosEvent {
    @Override public String getName() { return "Diamond Block";}
    @Override public int getWeight() { return 15; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.DIAMOND_BLOCK, 1);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§b\uD83D\uDC8E Chaos donated you a Diamond Block!!"));
    }
}
