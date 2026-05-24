package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TropicalFishEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Tropical Fish";
    }

    @Override
    public int getWeight() {
        return 40;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.TROPICAL_FISH, 1);
        if (InventoryHelper.tryAddItem(player, item, "Tropical Fish")) {
            player.sendSystemMessage(Component.literal("§b\uD83D\uDC20 Chaos donated you a tropical fish!"));
        }
    }
}
