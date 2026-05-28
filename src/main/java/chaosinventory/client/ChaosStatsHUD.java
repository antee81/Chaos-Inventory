package chaosinventory.client;

import chaosinventory.ChaosInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ChaosInventory.MODID, value = Dist.CLIENT)
public class ChaosStatsHUD {

    private static final Map<UUID, Integer> showTicks = new HashMap<>();
    private static final int DISPLAY_TICKS = 20 * 20;
    private static final int FADE_START_SECONDS = 15 * 20; // Inizia fade 5 secondi prima della scadenza

    public static void showMessage(UUID playerUUID) {
        showTicks.put(playerUUID, DISPLAY_TICKS);
        ChaosInventory.LOGGER.info("HUD message started for 20 seconds (fade last 5 seconds)");
    }

    @SubscribeEvent
    public static void onRenderHUD(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.EXPERIENCE_BAR.type()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        UUID uuid = mc.player.getUUID();
        if (!showTicks.containsKey(uuid)) return;

        int ticksRemaining = showTicks.get(uuid);

        if (ticksRemaining <= 0) {
            showTicks.remove(uuid);
            return;
        }
        showTicks.put(uuid, ticksRemaining - 1);

        int alpha = 255;
        if (ticksRemaining <= FADE_START_SECONDS) {
            float progress = (float) ticksRemaining / FADE_START_SECONDS;
            alpha = (int) (255 * progress);
        }

        if (alpha < 10) return;

        GuiGraphics graphics = event.getGuiGraphics();
        Font font = mc.font;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        String message = "\u00A77\uD83D\uDCCA Use \u00A7e/chaos stats \u00A77to see your progress!";
        int x = (screenWidth - font.width(message)) / 2;
        int y = screenHeight - 52;

        int color = (alpha << 24) | 0xAAAAAA;
        graphics.drawString(font, message, x, y, color, true);
    }
}
