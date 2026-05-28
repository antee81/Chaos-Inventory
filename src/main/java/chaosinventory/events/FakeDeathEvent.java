package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class FakeDeathEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Fake_Death";
    }

    @Override
    public int getWeight() {
        return 15;
    }

    @Override
    public boolean isMultiplayerOnly() {
        return true;
    }

    @Override
    public void execute(ServerPlayer player) {
        String message = "§4§l" + player.getName().getString() + "§c§l died in an embarrassing death in the chaos!";

        for (ServerPlayer online : player.serverLevel().getServer().getPlayerList().getPlayers()) {
            online.sendSystemMessage(Component.literal(message));
        }

        player.sendSystemMessage(Component.literal("§2✨ You fell for it.. you're alive!"));
    }
}
