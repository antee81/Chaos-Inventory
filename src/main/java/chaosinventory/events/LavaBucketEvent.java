package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class LavaBucketEvent implements ChaosEvent {
    @Override public String getName() { return "Lava Bucket"; }
    @Override public int getWeight() { return 5; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.LAVA_BUCKET, 1);
        if (!player.getInventory().add(item)) player.drop(item, false);
        player.sendSystemMessage(Component.literal("§c\uD83D\uDD25 Chaos donated you a lava bucket.. BE CAREFUL!"));
    }
}
