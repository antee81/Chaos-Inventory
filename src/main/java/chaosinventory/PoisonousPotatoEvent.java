package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.core.jmx.Server;

public class PoisonousPotatoEvent implements ChaosEvent{

    @Override
    public String getName() {
        return "Poisonous Potato";
    }

    @Override
    public int getWeight() {
        return 40;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack potato = new ItemStack(Items.POISONOUS_POTATO, 1);
        if (!player.getInventory().add(potato)) {
            player.drop(potato, false);
        }
        player.sendSystemMessage(Component.literal("§7\uD83E\uDD54 Chaos donated you.. a poisonuos potato!"));
    }
}
