package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cow;

public class CowRainEvent implements ChaosEvent {
    @Override public String getName() { return "Cow Rain"; }
    @Override public int getWeight() { return 15; }

    @Override
    public void execute(ServerPlayer player) {
        for (int i = 0; i < 20; i++) {
            Cow cow = EntityType.COW.create(player.serverLevel());
            if (cow != null) {
                cow.moveTo(player.getX(), player.getY() + 20, player.getZ());
                player.serverLevel().addFreshEntity(cow);
            }
        }
        EffectHelper.playTrollSound(player);
        EffectHelper.spawnTrollParticles(player);
        player.sendSystemMessage(Component.literal("§f\uD83D\uDC04 COW RAIN! Pay attention above your head!"));
    }
}
