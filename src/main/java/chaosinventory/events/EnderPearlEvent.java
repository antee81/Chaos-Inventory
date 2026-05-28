package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EnderPearlEvent implements ChaosEvent {
    @Override public String getName() { return "Ender Pearl"; }
    @Override public int getWeight() { return 25; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.ENDER_PEARL, 1);
        if (InventoryHelper.tryAddItem(player, item, "Ender_Pearl")) {
            EffectHelper.playGoodSound(player);
            EffectHelper.spawnGoodParticles(player);
            player.sendSystemMessage(Component.literal("§5\uD83D\uDFE3 Chaos donated you 1 Ender Pearl!"));
        }
    }
}
