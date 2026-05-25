package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CakeEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Cake";
    }

    @Override
    public int getWeight() {
        return 25;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.CAKE, 1);
        if (InventoryHelper.tryAddItem(player, item, "Cake")) {
            EffectHelper.playEatSound(player);
            EffectHelper.spawnGoodParticles(player);
            player.sendSystemMessage(Component.literal("§d\uD83C\uDF82 Chaos donated you a cake!"));
        }
    }
}
