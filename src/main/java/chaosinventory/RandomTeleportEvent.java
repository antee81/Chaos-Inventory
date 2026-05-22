package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Random;

public class RandomTeleportEvent implements ChaosEvent {
    @Override public String getName() { return "Random TP"; }
    @Override public int getWeight() { return 5; }

    @Override
    publid void execute(ServerPlayer player) {
        Random r = new Random();
        double x = player.getX() + (r.nextInt(1000) - 500);
        double z = player.getZ() + (r.nextInt(1000) - 500);
player.serverLevel().getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int)x,
        (int)z);
                player.teleportTo(x, y + 1, z);
                player.sendSystemMessage(Component.literal("§5\uD83C\uDFB2 Chaos teleported you away!"));
    }
}
