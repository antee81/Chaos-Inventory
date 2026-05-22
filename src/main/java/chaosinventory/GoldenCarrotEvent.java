package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.jmx.Server;

public class GoldenCarrotEvent implements ChaosEvent {
    @Override public String getName() { return "Golden Carrot"; }
    @Override public int getWeight() { return 40; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.GOLDEN_CARROT, 5);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§6\uD83E\uDD55 Chaos donated you 5 golden carrots!"));
    }
}
