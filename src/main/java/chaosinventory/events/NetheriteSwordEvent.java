package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;

public class NetheriteSwordEvent implements ChaosEvent {
    @Override public String getName() { return "Netherite Sword Sharp V"; }
    @Override public int getWeight() { return 5; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack sword = new ItemStack(Items.NETHERITE_SWORD, 1);
        sword.enchant(Enchantments.SHARPNESS, 5);
        if (InventoryHelper.tryAddItem(player, sword, "Netherite Sword Sharp V")) {
            player.sendSystemMessage(Component.literal("§c⚔ Chaos just donated you a Netherite Sword with Sharp V! You're so lucky."));
        }
    }
}
