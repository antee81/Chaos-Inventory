package chaosinventory.events;

import chaosinventory.ChaosEvent;
import chaosinventory.utils.EffectHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class HungerEffectEvent implements ChaosEvent {
    @Override public String getName() { return "Hunger III"; }
    @Override public int getWeight() { return 40; }

    @Override
    public void execute(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 20, 2));
        EffectHelper.playBadSound(player);
        EffectHelper.spawnBadParticles(player);
        player.sendSystemMessage(Component.literal("§4\uD83C\uDF56 Chaos has struck you with Hunger!"));
    }
}
