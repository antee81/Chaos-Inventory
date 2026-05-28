package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DiamondRainEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Diamond_Rain";
    }

    @Override
    public int getWeight() {
        return 2;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.DIAMOND, 10);
        if (InventoryHelper.tryAddItem(player, item, "Diamonds")) {
            EffectHelper.playEpicSound(player);
            EffectHelper.spawnEpicParticles(player);
            player.sendSystemMessage(Component.literal("§b\uD83D\uDC8E DIAMOND RAIN!! You received 10 diamonds!"));
        }
    }
}
