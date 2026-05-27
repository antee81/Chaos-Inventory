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

    private static final Map<UUID, Long> showuUntil = new HashMap<>();
    private static final int DISPLAY_DURATION = 20 * 1000;

    public static void showMessage(UUID playerUUID) {
        showuUntil.put(playerUUID, System.currentTimeMillis() + DISPLAY_DURATION);
    }

    @SubscribeEvent
    public static void onRenderHUD(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.EXPERIENCE_BAR.type()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        GuiGraphics graphics = event.getGuiGraphics();
        Font font = mc.font;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        String message = "§7📊 Usa §e/chaos stats §7per vedere progressione";
        int x = (screenWidth - font.width(message)) / 2;
        int y = screenHeight - 55;

        graphics.drawString(font, message, x, y, 0xAAAAAA, true);
    }
}
