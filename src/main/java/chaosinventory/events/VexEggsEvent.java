package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class VexEggsEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Vex Eggs x3";
    }

    @Override
    public int getWeight() {
        return 15;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.VEX_SPAWN_EGG, 3);
        if (InventoryHelper.tryAddItem(player, item, "3 Vex Eggs")) {
            player.sendSystemMessage(Component.literal("§b\uD83D\uDE08 Chaos just donated 3 Vex Eggs!"));
        }
    }
}
