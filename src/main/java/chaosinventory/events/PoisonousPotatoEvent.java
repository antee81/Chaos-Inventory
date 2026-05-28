package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PoisonousPotatoEvent implements ChaosEvent {

    @Override
    public String getName() {
        return "Poisonous_Potato";
    }

    @Override
    public int getWeight() {
        return 40;
    }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack item = new ItemStack(Items.POISONOUS_POTATO, 1);
        if (InventoryHelper.tryAddItem(player, item, "Poisonous Potato")) {
            EffectHelper.playTrollSound(player);
            EffectHelper.spawnTrollParticles(player);
            player.sendSystemMessage(Component.literal("§7\uD83E\uDD54 Chaos donated you.. a poisonuos potato!"));
        }
    }
}
