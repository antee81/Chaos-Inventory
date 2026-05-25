package chaosinventory.utils;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import chaosinventory.sound.ModSounds;

import javax.swing.text.html.HTMLDocument;

public class EffectHelper {
    public static void playGoodSound(ServerPlayer player) {
        player.playNotifySound(SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public static void playBadSound(ServerPlayer player) {
        player.playNotifySound(SoundEvents.GENERIC_HURT, SoundSource.PLAYERS, 1.0f, 0.8f);
    }

    public static void playTrollSound(ServerPlayer player) {
        player.playNotifySound(SoundEvents.CHICKEN_EGG, SoundSource.PLAYERS, 1.0f, 1.2f);
    }

    public static void playEpicSound(ServerPlayer player) {
        player.playNotifySound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.5f, 1.0f);
    }

    public static void playExplosionSound(ServerPlayer player) {
        player.playNotifySound(SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public static void playTeleportSound(ServerPlayer player) {
        player.playNotifySound(SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public static void playFireSound(ServerPlayer player) {
        player.playNotifySound(SoundEvents.FIRE_AMBIENT, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public static void playDrinkSound(ServerPlayer player) {
        player.playNotifySound(SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0f, 1.0f);
    }
    public static void playEatSound(ServerPlayer player) {
        player.playNotifySound(SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public static void spawnGoodParticles(ServerPlayer player) {
        Level level = player.serverLevel();
        for (int i = 0; i < 20; i++) {
            double x = player.getX() + (Math.random() - 0.5) * 2;
            double y = player.getY() + Math.random() * 2;
            double z = player.getZ() + (Math.random() - 0.5) * 2;
            level.addParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, 0, 0.1, 0);
        }
    }

    public static void spawnBadParticles(ServerPlayer player) {
        Level level = player.serverLevel();
        for (int i = 0; i < 15; i++) {
            double x = player.getX() + (Math.random() - 0.5) * 2;
            double y = player.getY() + Math.random() * 2;
            double z = player.getZ() + (Math.random() - 0.5) * 2;
            level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0.1, 0);
        }
    }

    public static void spawnTrollParticles(ServerPlayer player) {
        Level level = player.serverLevel();
        for (int i = 0; i < 10; i++) {
            double x = player.getX() + (Math.random() - 0.5) * 2;
            double y = player.getY() + Math.random() * 2;
            double z = player.getZ() + (Math.random() - 0.5) * 2;
            level.addParticle(ParticleTypes.POOF, x, y, z, 0, 0.1, 0);
        }
    }

    public static void spawnEpicParticles(ServerPlayer player) {
        Level level = player.serverLevel();
        for (int i = 0; i < 30; i++) {
            double x = player.getX() + (Math.random() - 0.5) * 3;
            double y = player.getY() + Math.random() * 3;
            double z = player.getZ() + (Math.random() - 0.5) * 3;
            level.addParticle(ParticleTypes.FIREWORK, x, y, z, 0, 0.2, 0);
        }
    }

    public static void spawnExplosionParticles(ServerPlayer player) {
        Level level = player.serverLevel();
        for (int i = 0; i < 25; i++) {
            double x = player.getX() + (Math.random() - 0.5) * 4;
            double y = player.getY() + Math.random() * 3;
            double z = player.getZ() + (Math.random() - 0.5) * 4;
            level.addParticle(ParticleTypes.EXPLOSION, x, y, z, 0, 0, 0);
        }
    }

    public static void spawnTeleportParticles(ServerPlayer player) {
        Level level = player.serverLevel();
        for (int i = 0; i < 20; i++) {
            double x = player.getX() + (Math.random()- 0.5) * 2;
            double y = player.getY() + Math.random() * 2;
            double z = player.getZ() + (Math.random() - 0.5) * 2;
            level.addParticle(ParticleTypes.PORTAL, x, y, z, 0, 0, 0);
        }
    }

    public static void spawnLightningParticles(ServerPlayer player) {
        Level level = player.serverLevel();
        for (int i = 0; i < 15; i++) {
            double x = player.getX() + (Math.random() - 0.5) * 3;
            double y = player.getY() + Math.random() * 3;
            double z = player.getZ() + (Math.random() - 0.5) * 3;
            level.addParticle(ParticleTypes.ELECTRIC_SPARK, x, y, z, 0, 0.1, 0);
        }
    }
}
