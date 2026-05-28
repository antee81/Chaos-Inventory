package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TotemEvent implements ChaosEvent {
    @Override public String getName() { return "Totem"; }
    @Override public int getWeight() {  return 5; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.TOTEM_OF_UNDYING, 1);
        if (InventoryHelper.tryAddItem(player, item, "Totem of Undying")) {
            EffectHelper.playEpicSound(player);
            EffectHelper.spawnEpicParticles(player);
            player.sendSystemMessage(Component.literal("§e✨ Chaos donated a Totem of Undying!"));
        }
    }
}
