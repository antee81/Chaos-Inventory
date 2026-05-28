package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BrokenCompassEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Broken_Compass";
    }

    @Override
    public int getWeight() {
        return 25;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.COMPASS, 1);
        item.setHoverName(Component.literal("§cBroken Compass"));
        if (InventoryHelper.tryAddItem(player, item, "Broken Compass")) {
            EffectHelper.playTrollSound(player);
            EffectHelper.spawnTrollParticles(player);
            player.sendSystemMessage(Component.literal("§7\uD83E\uDDED Chaos just donated you a compass... broken."));
        }
    }
}
