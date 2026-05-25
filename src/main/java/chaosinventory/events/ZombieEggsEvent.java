package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ZombieEggsEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Zombie Egg x10";
    }

    @Override
    public int getWeight() {
        return 25;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.ZOMBIE_SPAWN_EGG, 10);
        if (InventoryHelper.tryAddItem(player, item, "10 Zombie Eggs")) {
            EffectHelper.playTrollSound(player);
            EffectHelper.spawnTrollParticles(player);
            player.sendSystemMessage(Component.literal("§2\uD83E\uDDDF Chaos just donated you 10 Zombie Eggs!"));
        }
    }
}
