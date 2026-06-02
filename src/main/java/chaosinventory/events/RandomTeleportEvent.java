package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Random;

public class RandomTeleportEvent implements ChaosEvent {
    @Override public String getName() { return "Random_Teleport"; }
    @Override public int getWeight() { return 5; }

    @Override
    public void execute(ServerPlayer player) {
        Random r = new Random();
        ServerLevel level = player.serverLevel();

        int x = (int) player.getX() + (r.nextInt(500) - 250);
        int z = (int) player.getZ() + (r.nextInt(500) - 250);

        int y = level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

        if (y < 62) y = 62;

        while (y > 60 && level.getBlockState(new BlockPos(x, y - 1, z)).isAir()) {
            y--;
        }

        player.teleportTo(x + 0.5, y + 1, z + 0.5);

        EffectHelper.playTeleportSound(player);
        EffectHelper.spawnTeleportParticles(player);
        player.sendSystemMessage(Component.literal("§5\uD83C\uDFB2 Chaos teleported you far away!"));
    }
}


