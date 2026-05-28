package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import chaosinventory.utils.EffectHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GoldenAppleEvent implements ChaosEvent {
    @Override public String getName() { return "Golden_Apple";}
    @Override public int getWeight() { return 25;}

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.GOLDEN_APPLE, 1);
        if (InventoryHelper.tryAddItem(player, item, "Golden Apple")) {
            EffectHelper.playGoodSound(player);
            EffectHelper.spawnGoodParticles(player);
            player.sendSystemMessage(Component.literal("§6\uD83C\uDF4E Chaos donated you a Golden Apple!"));
        }
    }
}
