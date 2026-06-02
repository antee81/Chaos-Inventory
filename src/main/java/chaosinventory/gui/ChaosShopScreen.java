package chaosinventory.gui;

import chaosinventory.economy.ChaosEconomy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ChaosShopScreen extends Screen {

    private static final int GUI_WIDTH = 220;
    private static final int GUI_HEIGHT = 290;

    private int guiLeft;
    private int guiTop;

    private final ShopItem[] items = {
            new ShopItem(new ItemStack(Items.DIAMOND), 50, "Diamond"),
            new ShopItem(new ItemStack(Items.DIAMOND_BLOCK), 450, "Diamond Block"),
            new ShopItem(new ItemStack(Items.GOLDEN_APPLE), 75, "Golden Apple"),
            new ShopItem(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE), 300, "Enchanted Apple"),
            new ShopItem(new ItemStack(Items.ELYTRA), 1000, "Elytra"),
            new ShopItem(new ItemStack(Items.TOTEM_OF_UNDYING), 500, "Totem"),
            new ShopItem(new ItemStack(Items.EXPERIENCE_BOTTLE, 3), 30, "XP Bottles x3"),
            new ShopItem(new ItemStack(Items.SHIELD), 40, "Shield"),
            new ShopItem(new ItemStack(Items.NETHER_STAR), 5000, "Chaos Token"),
            new ShopItem(new ItemStack(Items.CLOCK), 200, "Timer Freeze"),
            new ShopItem(new ItemStack(Items.GOLDEN_CARROT), 300, "Double XP"),
    };

    public ChaosShopScreen() {
        super(Component.literal("Chaos Shop"));
    }

    @Override
    protected void init() {
        super.init();
        this.guiLeft = (this.width - GUI_WIDTH) / 2;
        this.guiTop = (this.height - GUI_HEIGHT) / 2;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics);

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        int coins = ChaosEconomy.getCoins(player.getUUID());

        // Sfondo principale
        graphics.fill(guiLeft, guiTop, guiLeft + GUI_WIDTH, guiTop + GUI_HEIGHT, 0xC0000000);
        graphics.fill(guiLeft + 5, guiTop + 5, guiLeft + GUI_WIDTH - 5, guiTop + GUI_HEIGHT - 5, 0xFF111111);

        // Titolo
        graphics.drawCenteredString(font, Component.literal("§6§lCHAOS SHOP"), guiLeft + GUI_WIDTH / 2, guiTop + 12, 0xFFFFFF);

        // Monete (corretto: §6 invece di $6)
        String coinText = "💰 " + coins + " coins";
        graphics.drawString(font, Component.literal("§6" + coinText), guiLeft + GUI_WIDTH - font.width(coinText) - 10, guiTop + 14, 0xFFFFFF);

        // Linea separatrice
        graphics.fill(guiLeft + 10, guiTop + 28, guiLeft + GUI_WIDTH - 10, guiTop + 29, 0x444444);

        // Lista items
        int yOffset = 38;
        int slotHeight = 22;

        for (int i = 0; i < items.length; i++) {
            ShopItem shopItem = items[i];
            int y = guiTop + yOffset + (i * slotHeight);

            // Sfondo slot
            if (mouseX >= guiLeft + 8 && mouseX < guiLeft + GUI_WIDTH - 8 && mouseY >= y && mouseY < y + slotHeight) {
                graphics.fill(guiLeft + 8, y, guiLeft + GUI_WIDTH - 8, y + slotHeight, 0x44FFAA00);
            }

            // Icona
            graphics.renderItem(shopItem.item, guiLeft + 12, y + 3);
            graphics.renderItemDecorations(font, shopItem.item, guiLeft + 12, y + 3);

            // Nome
            graphics.drawString(font, Component.literal(shopItem.name), guiLeft + 32, y + 5, 0xFFFFFF);

            // Prezzo (corretto: §6 invece di $6)
            String priceText = "§6" + shopItem.price + " coins";
            int priceWidth = font.width(priceText);
            graphics.drawString(font, Component.literal(priceText), guiLeft + GUI_WIDTH - priceWidth - 12, y + 6, 0xFFFFFF);

            // Messaggio hover
            if (mouseX >= guiLeft + 8 && mouseX < guiLeft + GUI_WIDTH - 8 && mouseY >= y && mouseY < y + slotHeight) {
                graphics.drawString(font, Component.literal("§7Click to buy"), guiLeft + 32, y + 14, 0xAAAAAA);
            }
        }

        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return false;

        int yOffset = 38;
        int slotHeight = 22;

        for (int i = 0; i < items.length; i++) {
            ShopItem shopItem = items[i];
            int y = guiTop + yOffset + (i * slotHeight);

            if (mouseX >= guiLeft + 8 && mouseX < guiLeft + GUI_WIDTH - 8 && mouseY >= y && mouseY < y + slotHeight) {
                int coins = ChaosEconomy.getCoins(player.getUUID());

                if (coins >= shopItem.price) {
                    ChaosEconomy.removeCoins(player.getUUID(), shopItem.price);
                    ItemStack bought = shopItem.item.copy();
                    if (!player.getInventory().add(bought)) {
                        player.drop(bought, false);
                    }
                    player.sendSystemMessage(Component.literal("§a✅ You bought §e" + shopItem.name + "§a for §6" + shopItem.price + " coins§a!"));
                    Minecraft.getInstance().setScreen(this);
                } else {
                    player.sendSystemMessage(Component.literal("§c❌ You need §e" + (shopItem.price - coins) + "§c more coins!"));
                }
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private static class ShopItem {
        ItemStack item;
        int price;
        String name;

        ShopItem(ItemStack item, int price, String name) {
            this.item = item;
            this.price = price;
            this.name = name;
        }
    }
}