package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class LightningStrikeEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Lightning Strike";
    }

    @Override
    public int getWeight() {
        return 7;
    }

    @Override
    public void execute(ServerPlayer player) {
        float currentHealth = player.getHealth();
        float damage = Math.min(4.0f, currentHealth - 1.0f);

        if (damage > 0) {
            player.hurt(player.damageSources().lightningBolt(), damage);
        }

        player.setSecondsOnFire(2);

        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 3, 0));
        BlockPos pos = player.blockPosition();
        player.serverLevel().setWeatherParameters(0, 40, true, false);

        player.sendSystemMessage(Component.literal("§e⚡ A lightning bolt grazes you! §6(You lost " + (damage/2) + " hearts)"));
    }
}
