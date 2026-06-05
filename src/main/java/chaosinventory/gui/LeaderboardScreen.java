package chaosinventory.gui;

import chaosinventory.leaderboard.LeaderboardManager;
import chaosinventory.utils.LanguageManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class LeaderboardScreen extends Screen {
    private static final int GUI_WIDTH = 350;
    private static final int GUI_HEIGHT = 280;

    private int guiLeft;
    private int guiTop;
    private List<String> leaderboardLines;
    private int scrollOffset = 0;
    private int maxScroll = 0;

    public LeaderboardScreen() {
        super(Component.literal("Leaderboard"));
    }

    @Override
    protected void init() {
        super.init();
        this.guiLeft = (this.width - GUI_WIDTH) / 2;
        this.guiTop = (this.height - GUI_HEIGHT) / 2;

        Player player = Minecraft.getInstance().player;
        if (player != null) {
            leaderboardLines = LeaderboardManager.getLeaderboard(player);
            maxScroll = Math.max(0, (leaderboardLines.size() * 10) - (GUI_HEIGHT - 60));
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics);

        graphics.fill(guiLeft, guiTop, guiLeft + GUI_WIDTH, guiTop + GUI_HEIGHT, 0xC0000000);
        graphics.fill(guiLeft + 5, guiTop + 5, guiLeft + GUI_WIDTH - 5, guiTop + GUI_HEIGHT - 5, 0xFF111111);
        graphics.drawCenteredString(font, Component.literal(LanguageManager.get("chaos.leaderboard.title")), guiLeft + GUI_WIDTH / 2, guiTop + 12, 0xFFFFFF);
        graphics.fill(guiLeft + 10, guiTop + 28, guiLeft + GUI_WIDTH - 10, guiTop + 29, 0x444444);

        int yOffset = guiTop + 38;
        int lineHeight = 10;

        if (leaderboardLines != null) {
            for (int i = 0; i < leaderboardLines.size(); i++) {
                int y = yOffset + (i * lineHeight) - scrollOffset;
                if (y >= guiTop + 35 && y < guiTop + GUI_HEIGHT - 15) {
                    graphics.drawString(font, Component.literal(leaderboardLines.get(i)), guiLeft + 12, y, 0xFFFFFF);
                }
            }
        }

        // graphics.drawString(font, Component.literal("§7Scroll mouse to scroll"), guiLeft + 12, guiTop + GUI_HEIGHT - 12, 0x444444);

        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        scrollOffset -= delta * 10;
        scrollOffset = Math.max(0, Math.min(scrollOffset, maxScroll));
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
