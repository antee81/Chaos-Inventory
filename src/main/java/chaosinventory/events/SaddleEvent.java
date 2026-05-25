package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SaddleEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Saddle";
    }

    @Override
    public int getWeight() {
        return 25;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.SADDLE, 1);
        if (InventoryHelper.tryAddItem(player, item, "Saddle")) {
            EffectHelper.playTrollSound(player);
            EffectHelper.spawnTrollParticles(player);
            player.sendSystemMessage(Component.literal("§6\uD83D\uDC34 Chaos donated you a saddle!"));
        }
    }
}
