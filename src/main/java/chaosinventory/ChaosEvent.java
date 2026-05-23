package chaosinventory;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChaosInventory.MODID)
public class ChaosEvent {

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.getServer() == null) return;

        ChaosTimer.tick(event.getServer());
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ChaosTimer.initPlayer(player.getUUID());
            ChaosInventory.LOGGER.info("\uD83C\uDF00 Player joined: " + player.getName().getString());
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ChaosTimer.removePlayer(player.getUUID());
            ChaosInventory.LOGGER.info("\uD83C\uDF00 Player left: " + player.getName().getString());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ChaosTimer.resumePlayer(player.getUUID());
            ChaosInventory.LOGGER.info("\uD83C\uDF00 Player respawned, timer resumed: " + player.getName().getString());
        }
    }
}
