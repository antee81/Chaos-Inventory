package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ElytraEvent implements ChaosEvent {
    @Override public String getName() { return "Elytra"; }
    @Override public int getWeight() { return 2; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.ELYTRA, 1);
        if (InventoryHelper.tryAddItem(player, item, "Elytra")) {
            player.sendSystemMessage(Component.literal("§d\uD83E\uDEB6 CHAOS DONATED YOU AN ELYTRA!"));
        }
    }
}
