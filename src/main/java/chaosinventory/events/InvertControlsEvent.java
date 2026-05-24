package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class InvertControlsEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Invert Controls";
    }

    @Override
    public int getWeight() {
        return 12;
    }

    @Override
    public void execute(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 15, 0));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 15, 0));
        player.sendSystemMessage(Component.literal("§7\uD83C\uDFAE Your controls have been inverted for 15 seconds"));
    }
}
