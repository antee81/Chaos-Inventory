package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShuffleInventoryEvent implements ChaosEvent {
    @Override public String getName() { return "Shuffled_Inventory"; }
    @Override public int getWeight() { return 25; }

    @Override
    public void execute(ServerPlayer player) {
        Inventory inv = player.getInventory();
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < inv.items.size(); i++) {
            items.add(inv.items.get(i));
        }
        Collections.shuffle(items);
        for (int i = 0; i < inv.items.size(); i++) {
            inv.items.set(i, items.get(i));
        }
        EffectHelper.playTrollSound(player);
        EffectHelper.spawnTrollParticles(player);
        player.sendSystemMessage(Component.literal("§5🎴 Chaos shuffled your inventory!"));
    }
}
