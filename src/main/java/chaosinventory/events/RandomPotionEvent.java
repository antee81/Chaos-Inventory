package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomPotionEvent implements ChaosEvent {
    private static final List<MobEffect> EFFECTS = Arrays.asList(
            MobEffects.MOVEMENT_SPEED,
            MobEffects.DAMAGE_BOOST,
            MobEffects.REGENERATION,
            MobEffects.NIGHT_VISION,
            MobEffects.JUMP,
            MobEffects.WEAKNESS,
            MobEffects.POISON,
            MobEffects.HUNGER,
            MobEffects.BLINDNESS,
            MobEffects.DIG_SLOWDOWN
    );
    private static final Random RANDOM = new Random();

    @Override
    public String getName() {
        return "Random_Potion";
    }

    @Override
    public int getWeight() {
        return 18;
    }

    @Override
    public void execute(ServerPlayer player) {
        MobEffect randomEffect = EFFECTS.get(RANDOM.nextInt(EFFECTS.size()));
        boolean isPositive = isPositiveEffect(randomEffect);

        player.addEffect(new MobEffectInstance(randomEffect, 20 * 20, 1));

        if (isPositive) {
            player.sendSystemMessage(Component.literal("§a✨ Chaos decided to give you a positive effect: §e" + getEffectName(randomEffect)));
        } else {
            player.sendSystemMessage(Component.literal("§4\uD83D\uDC80 Chaos decided to hit you with a negative effect: §c" + getEffectName(randomEffect)));
        }
    }

    private boolean isPositiveEffect(MobEffect effect) {
        return effect == MobEffects.MOVEMENT_SPEED || effect == MobEffects.DAMAGE_BOOST ||
                effect == MobEffects.REGENERATION || effect == MobEffects.NIGHT_VISION ||
                effect == MobEffects.JUMP;
    }

    private String getEffectName(MobEffect effect) {
        if (effect == MobEffects.MOVEMENT_SPEED) return "Speed";
        if (effect == MobEffects.DAMAGE_BOOST) return "Strength";
        if (effect == MobEffects.REGENERATION) return "Regeneration";
        if (effect == MobEffects.NIGHT_VISION) return "Night Vision";
        if (effect == MobEffects.JUMP) return "Super Jump";
        if (effect == MobEffects.WEAKNESS) return "Weakness";
        if (effect == MobEffects.POISON) return "Poison";
        if (effect == MobEffects.HUNGER) return "Hunger";
        if (effect == MobEffects.BLINDNESS) return "Blindness";
        if (effect == MobEffects.DIG_SLOWDOWN) return "Mining Fatigue";
        return "Unknown";
    }
}
