package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class WitherSkeletonEggsEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Wither Skeleton Eggs";
    }

    @Override
    public int getWeight() {
        return 5;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.WITHER_SKELETON_SPAWN_EGG, 1);
        if (InventoryHelper.tryAddItem(player, item, "Wither Skeleton Egg")) {
            player.sendSystemMessage(Component.literal("§0\uD83D\uDC80 Chaos just donated you a Wither Skeleton Egg!"));
        }
    }
}
