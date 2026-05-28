package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CreeperHeadEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Creeper_Head";
    }

    @Override
    public int getWeight() {
        return 15;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.CREEPER_HEAD, 1);
        if (InventoryHelper.tryAddItem(player, item, "1 Creeper Head")) {
            EffectHelper.playTrollSound(player);
            EffectHelper.spawnTrollParticles(player);
            player.sendSystemMessage(Component.literal("§2\uD83D\uDC80 Chaos donated you a creeper's head.. it seems disturbing.."));
        }
    }
}
