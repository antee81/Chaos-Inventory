package chaosinventory;

import chaosinventory.config.ChaosConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = ChaosInventory.MODID, value = Dist.CLIENT)
public class ChaosHUD {

    @SubscribeEvent
    public static void onRenderHUD(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.EXPERIENCE_BAR.type()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        UUID uuidObj = mc.player.getUUID();

        String uuidStr = mc.player.getStringUUID();

        if (mc.player.isDeadOrDying()) return;
        if (!ChaosTimer.isRunning(uuidObj)) return;

        GuiGraphics graphics = event.getGuiGraphics();
        Font font = mc.font;

        int ticksLeft = ChaosTimer.getTicksRemaining(uuidObj);
        int secondsLeft = ticksLeft / 20;
        String time = ChaosTimer.getTimeFormatted(uuidObj);


        String colorName = ChaosConfig.getPlayerTimerColor(uuidStr);
        int timerColor = ChaosConfig.COLOR_CODES.getOrDefault(colorName, 0xFFFFFFFF);


        if (secondsLeft <= 10) {

            timerColor = (System.currentTimeMillis() / 250) % 2 == 0 ? 0xFFFF0000 : timerColor;
        } else if (secondsLeft <= 60) {
            if ("WHITE".equals(colorName)) timerColor = 0xFFFF8800;
        } else if (secondsLeft <= 300) {
            if ("WHITE".equals(colorName)) timerColor = 0xFFFFFF00;
        }

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int totalWidth = font.width(time);
        int x = (screenWidth - totalWidth) / 2;
        int y = screenHeight - 75;

        graphics.drawString(font, time, x, y, timerColor, true);
    }
}