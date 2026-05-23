package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TntEvent implements ChaosEvent {

    @Override
    public String getName() {
        return "TNT Donated";
    }

    @Override
    public int getWeight() {
        return 25;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack tnt = new ItemStack(Items.TNT, 3);

        if (!player.getInventory().add(tnt)) player.drop(tnt, false);

        player.sendSystemMessage(Component.literal("§c\uD83D\uDCA5 Chaos donated you TNT.. used it with wisdom!"));
    }
}
