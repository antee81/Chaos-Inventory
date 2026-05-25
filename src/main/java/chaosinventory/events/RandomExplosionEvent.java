package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class RandomExplosionEvent implements ChaosEvent {
    @Override public String getName() { return "Random Explosion"; }
    @Override public int getWeight() { return 15; }

    @Override
    public void execute(ServerPlayer player) {
        float currentHealth = player.getHealth();

        player.serverLevel().explode(null,
                player.getX() + 2, player.getY(), player.getZ() + 2,
                1.5f, Level.ExplosionInteraction.NONE);

        if (player.getHealth() < 2.0f) {
            player.setHealth(2.0f);
        }
        EffectHelper.playExplosionSound(player);
        EffectHelper.spawnExplosionParticles(player);
        player.sendSystemMessage(Component.literal("§c\uD83D\uDCA5 Chaos exploded something near you!"));
    }
}
