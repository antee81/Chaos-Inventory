package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import chaosinventory.utils.InventoryHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

public class InvisibilityPotionEvent implements ChaosEvent {
    @Override public String getName() { return "Invisibility Potion"; }
    @Override public int getWeight() { return 25; }

    @Override
    public void execute(ServerPlayer player) {
        ItemStack potion = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.INVISIBILITY);
        if (InventoryHelper.tryAddItem(player, potion, "Invisibility Potion")) {
            EffectHelper.playGoodSound(player);
            EffectHelper.spawnGoodParticles(player);
            player.sendSystemMessage(Component.literal("§7\uD83D\uDC7B Chaos just donated you an Invisibility Potion!"));
        }
    }
}
