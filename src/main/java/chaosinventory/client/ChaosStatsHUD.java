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

    private static final Map<UUID, Long> startTime = new HashMap<>();
    private static final int DISPLAY_SECONDS = 20;
    private static final int FADE_SECONDS = 5;

    public static void showMessage(UUID playerUUID) {
        startTime.put(playerUUID, System.currentTimeMillis());
        ChaosInventory.LOGGER.info("📊 HUD message started for 20 seconds");
    }

    @SubscribeEvent
    public static void onRenderHUD(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.EXPERIENCE_BAR.type()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        UUID uuid = mc.player.getUUID();

        Long start = startTime.get(uuid);
        if (start == null) return;

        long elapsed = (System.currentTimeMillis() - start) / 1000;

        if (elapsed >= DISPLAY_SECONDS) {
            startTime.remove(uuid);
            return;
        }

        int alpha = 255;
        if (elapsed > DISPLAY_SECONDS - FADE_SECONDS) {
            float fadeProgress = (float)(DISPLAY_SECONDS - elapsed) / FADE_SECONDS;
            alpha = (int)(255 * fadeProgress);
            if (alpha < 0) alpha = 0;
        }

        GuiGraphics graphics = event.getGuiGraphics();
        Font font = mc.font;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        String message = "§7📊 Use §e/chaos stats §7to see your progress";
        int x = (screenWidth - font.width(message)) / 2;
        int y = screenHeight - 55;

        int color = (alpha << 24) | 0xAAAAAA;
        graphics.drawString(font, message, x, y, color, true);
    }
}
