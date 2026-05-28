package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class TimeWarpEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Time_Warp";
    }

    @Override
    public int getWeight() {
        return 10;
    }

    @Override
    public void execute(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 10, 1));
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 10, 1));
        player.sendSystemMessage(Component.literal("§b⚡ Chaos just doubled speed of time! Velocity Doubled!"));
    }
}
