package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class SlownessEffectEvent implements ChaosEvent {
    @Override public String getName() { return "Slowness IV"; }
    @Override public int getWeight() { return 40; }

    @Override
    public void execute(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 60, 3));
        player.sendSystemMessage(Component.literal("§7\uD83D\uDC0C Chaos slowed you down for 1 minute!"));
    }
}
