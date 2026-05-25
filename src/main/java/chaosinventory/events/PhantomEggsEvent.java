package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PhantomEggsEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Phantom Eggs x5";
    }

    @Override
    public int getWeight() {
        return 15;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.PHANTOM_SPAWN_EGG, 5);
        if (InventoryHelper.tryAddItem(player, item, "5 Phantom Eggs")) {
            EffectHelper.playTrollSound(player);
            EffectHelper.spawnTrollParticles(player);
            player.sendSystemMessage(Component.literal("§8\uD83D\uDC7B Chaos just donated 5 Phantom Eggs!"));
        }
    }
}
