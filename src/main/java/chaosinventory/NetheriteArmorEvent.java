package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class NetheriteArmorEvent implements ChaosEvent {
    @Override public String getName() { return "Complete Netherite Armor"; }
    @Override public int getWeight() { return 2; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack[] armor = {
                new ItemStack(Items.NETHERITE_HELMET),
                new ItemStack(Items.NETHERITE_CHESTPLATE),
                new ItemStack(Items.NETHERITE_LEGGINGS),
                new ItemStack(Items.NETHERITE_BOOTS)
        };
        for (ItemStack piece : armor) {
            if (!player.getInventory().add(piece)) player.drop(piece, false);
        }
        player.sendSystemMessage(Component.literal("§4\uD83D\uDEE1 CHAOS JUST DONATED YOU A FULL NETHERITE ARMOR!"));
    }
}
