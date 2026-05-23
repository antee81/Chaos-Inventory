package chaosinventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sun.jna.platform.win32.WinDef;
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

        UUID uuid = mc.player.getUUID();

        if (mc.player.isDeadOrDying()) return;
        if (!ChaosTimer.isRunning(uuid)) return;

        GuiGraphics graphics = event.getGuiGraphics();
        Font font = mc.font;

        int ticksLeft = ChaosTimer.getTicksRemaining();
        int secondsLeft = ticksLeft / 20;
        String time = ChaosTimer.getTimeFormatted();

        int timerColor;
        if (secondsLeft <= 10) {
            timerColor = (System.currentTimeMillis() / 250) % 2 == 0 ? 0xFFFF0000 : 0xFFFFFFFF;
        } else if (secondsLeft <= 60) {
            timerColor = 0xFFFF8800;
        } else if (secondsLeft <= 300) {
            timerColor = 0xFFFFFF00;
        } else {
            timerColor = 0xFFFFFFFF;
        }

        int labelColor = 0xFFFFFFFF;


        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int totalWidth = font.width(time);
        int x = (screenWidth - totalWidth) / 2;
        int y = screenHeight - 75;

        graphics.drawString(font, time, x, y, timerColor, true);
    }
}
