package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class DamagePotionEvent implements ChaosEvent {
    @Override public String getName() { return "Damage Potion"; }
    @Override public int getWeight() { return 15; }

    @Override
    public void execute(ServerPlayer player) {
        player.hurt(player.damageSources().magic(), 6.0f);
        player.sendSystemMessage(Component.literal("§4\uD83D\uDCA5 Chaos hit you with a damage potion!"));
    }
}
