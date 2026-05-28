package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DiamondsEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Pioggia di Diamanti";
    }

    @Override
    public int getWeight() {
        return 15; // RARO
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.DIAMOND, 5);
        if (InventoryHelper.tryAddItem(player, item, "Diamonds")) {
            EffectHelper.playGoodSound(player);
            EffectHelper.spawnGoodParticles(player);
            player.sendSystemMessage(Component.literal("§b\uD83D\uDC8E Chaos just donated you 5 diamonds!"));
        }
    }
}
