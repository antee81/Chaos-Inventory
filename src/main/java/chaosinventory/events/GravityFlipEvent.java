package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class GravityFlipEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Gravity Flip";
    }

    @Override
    public int getWeight() {
        return 10;
    }

    @Override
    public void execute(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 10, 0));
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20 *2, 0));
        player.sendSystemMessage(Component.literal("§5\uD83C\uDF00 Chaos has disoriented your senses!"));
    }
}
