package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SpectralArrowsEvent implements ChaosEvent {
    @Override public String getName() { return "Spectral Arrows"; }
    @Override public int getWeight() { return 40; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.SPECTRAL_ARROW, 8);
        if (InventoryHelper.tryAddItem(player, item, "8 Spectral Arrows")) {
            EffectHelper.playGoodSound(player);
            EffectHelper.spawnGoodParticles(player);
            player.sendSystemMessage(Component.literal("§f\uD83C\uDFF9 Chaos donated you 8 spectral arrows!"));
        }
    }
}
