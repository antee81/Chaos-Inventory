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
    private static final int FADE_START_TICKS = 5 * 20;

    public static void showMessage(UUID playerUUID) {
        showTicks.put(playerUUID, DISPLAY_TICKS);
        ChaosInventory.LOGGER.info("HUD message started for 20 seconds");
    }

    private static void tick() {
        if (Minecraft.getInstance().player == null) return;
        UUID uuid = Minecraft.getInstance().player.getUUID();

        if (showTicks.containsKey(uuid)) {
            int remaining = showTicks.get(uuid) - 1;
            if (remaining <= 0) {
                showTicks.remove(uuid);
            } else {
                showTicks.put(uuid, remaining);
            }
        }
    }

    private static int getAlpha(int ticksRemaining) {
        if (ticksRemaining > FADE_START_TICKS) {
            return 255;
        }
        float progress = (float) ticksRemaining / FADE_START_TICKS;
        return (int) (255 * progress);
    }

    @SubscribeEvent
    public static void onRenderHUD(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.EXPERIENCE_BAR.type()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        tick();

        UUID uuid = mc.player.getUUID();
        if (!showTicks.containsKey(uuid)) return;

        int ticksRemaining = showTicks.get(uuid);

        int alpha = 255;
        if (ticksRemaining <= FADE_START_TICKS) {
            float progress = (float) ticksRemaining / FADE_START_TICKS;
            alpha = (int) (255 * progress);
        }

        if (alpha < 10) return;

        GuiGraphics graphics = event.getGuiGraphics();
        Font font = mc.font;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        String message = "\u00A77\uD83D\uDCCA📊 Use \u00A7e/chaos stats \u00A77to see your progress";
        int x = (screenWidth - font.width(message)) / 2;
        int y = screenHeight - 55;

        int color = (alpha << 24) | 0xAAAAAA;

        graphics.drawString(font, message, x, y, 0xAAAAAA, true);
    }
}
