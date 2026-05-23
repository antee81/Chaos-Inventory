package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class BlindnessEffectEvent implements ChaosEvent {
    @Override public String getName() { return "Blindness"; }
    @Override public int getWeight() { return 25; }

    @Override
    public void execute(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20 * 30, 0));
        player.sendSystemMessage(Component.literal("§0\uD83D\uDC41 Chaos Chaos blinded you for 30 seconds!"));
    }
}
