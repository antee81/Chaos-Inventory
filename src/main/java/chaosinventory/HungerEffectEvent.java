package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class HungerEffectEvent implements ChaosEvent {
    @Override public String getName() { return "Hunger III"; }
    @Override public int getWeight() { return 40; }

    @Override
    public void execute(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 120, 2));
        player.sendSystemMessage(Component.literal("§4\uD83C\uDF56 Chaos has struck you with Hunger!"));
    }
}
