package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class IceEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Ice";
    }

    @Override
    public int getWeight() {
        return 25;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.ICE, 1);
        if (InventoryHelper.tryAddItem(player, item, "Ice")) {
            EffectHelper.playTrollSound(player);
            EffectHelper.spawnTrollParticles(player);
            player.sendSystemMessage(Component.literal("§b\uD83E\uDDCA Chaos donated you an ice block."));
        }
    }
}
