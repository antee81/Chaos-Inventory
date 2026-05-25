package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class NetheriteArmorEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Complete Netherite Armor";
    }

    @Override
    public int getWeight() {
        return 2;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack[] armor = {
                new ItemStack(Items.NETHERITE_HELMET),
                new ItemStack(Items.NETHERITE_CHESTPLATE),
                new ItemStack(Items.NETHERITE_LEGGINGS),
                new ItemStack(Items.NETHERITE_BOOTS)
        };
        boolean allAdded = true;
        for (ItemStack piece : armor) {
            if (!InventoryHelper.tryAddItem(player, piece, piece.getHoverName().getString())) {
                allAdded = false;
            }
        }
        if (allAdded) {
            EffectHelper.playEpicSound(player);
            EffectHelper.spawnEpicParticles(player);
            player.sendSystemMessage(Component.literal("§4🛡️ CHAOS JUST DONATED YOU A FULL NETHERITE ARMOR!"));
        }
    }
}
