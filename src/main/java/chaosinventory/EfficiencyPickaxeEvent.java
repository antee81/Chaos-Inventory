package chaosinventory.events;

import chaosinventory.ChaosEvent;
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
        if (!player.getInventory().add(pick)) player.drop(pick, false);
        player.sendSystemMessage(Component.literal("§b⛏ Chaos just donated you a Diamond Pickaxe, keep his efficiency"));
    }
}
