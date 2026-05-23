package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class EarthquakeEvent implements ChaosEvent {
    @Override public String getName() { return "Earthquake"; }
    @Override public int getWeight() { return 5; }

    @Override
    public void execute(ServerPlayer player) {
        player.setDeltaMovement(0, 2.0, 0);
        player.hurtMarked = true;
        player.sendSystemMessage(Component.literal("§6\uD83C\uDF0D EARTHQUAKE! Everyone in air!"));
    }
}
