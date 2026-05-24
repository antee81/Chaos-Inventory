package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CreeperEggsEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Creeper Eggs x3";
    }

    @Override
    public int getWeight() {
        return 15;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.CREEPER_SPAWN_EGG, 3);
        if (InventoryHelper.tryAddItem(player, item, "3 Creeper Eggs")) {
            player.sendSystemMessage(Component.literal("§a\uD83D\uDCA5 Chaos just donated you 3 Creeper Eggs. BOOOM!"));
        }
    }
}
