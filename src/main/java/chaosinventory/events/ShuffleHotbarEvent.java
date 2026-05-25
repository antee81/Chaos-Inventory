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

public class ShuffleHotbarEvent implements ChaosEvent {
    @Override public String getName() { return "Hotbar Shuffled"; }
    @Override public int getWeight() { return 40; }

    @Override
    public void execute(ServerPlayer player) {
        Inventory inv = player.getInventory();
        List<ItemStack> hotbar = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            hotbar.add(inv.items.get(i));
        }
        Collections.shuffle(hotbar);
        for (int i = 0; i < 9; i++) {
            inv.items.set(i, hotbar.get(i));
        }
        EffectHelper.playTrollSound(player);
        EffectHelper.spawnTrollParticles(player);
        player.sendSystemMessage(Component.literal("§e\uD83C\uDFB4 Chaos shuffled your hotbar!"));
    }
}
