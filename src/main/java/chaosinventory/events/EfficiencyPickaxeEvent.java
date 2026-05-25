package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;

public class EfficiencyPickaxeEvent implements ChaosEvent {
    @Override public String getName() { return "Efficiency Pickaxe"; }
    @Override public int getWeight() { return 15; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack pick = new ItemStack(Items.DIAMOND_PICKAXE, 1);
        pick.enchant(Enchantments.BLOCK_EFFICIENCY, 5);
        if (InventoryHelper.tryAddItem(player, pick, "Efficiency V Pickaxe")) {
            EffectHelper.playGoodSound(player);
            EffectHelper.spawnGoodParticles(player);
            player.sendSystemMessage(Component.literal("§b⛏ Chaos just donated you a Diamond Pickaxe, keep his efficiency"));
        }
    }
}
