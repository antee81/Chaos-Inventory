package chaosinventory.gui;

import chaosinventory.economy.ChaosEconomy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ChaosShopScreen extends Screen {

    private static final int GUI_WIDTH = 220;
    private static final int GUI_HEIGHT = 285;

    private int guiLeft;
    private int guiTop;

    private final ShopItem[] items = {
            new ShopItem("diamond", "§bDiamond", 50, "minecraft:diamond"),
            new ShopItem("diamond_block", "§bDiamond Block", 450, "minecraft:diamond_block"),
            new ShopItem("golden_apple", "§6Golden Apple", 75, "minecraft:golden_apple"),
            new ShopItem("enchanted_gapple", "§dEnchanted Apple", 300, "minecraft:enchanted_golden_apple"),
            new ShopItem("elytra", "§dElytra", 1000, "minecraft:elytra"),
            new ShopItem("totem", "§eTotem", 500, "minecraft:totem_of_undying"),
            new ShopItem("xp_bottle", "§aXP Bottles x3", 30, "minecraft:experience_bottle"),
            new ShopItem("shield", "§7Shield", 40, "minecraft:shield"),
            new ShopItem("chaos_token", "§5Chaos Token", 5000, "minecraft:nether_star"),
            new ShopItem("timer_freeze", "§3Timer Freeze", 200, "minecraft:clock"),
            new ShopItem("double_xp", "§eDouble XP", 300, "minecraft:golden_carrot"),
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

    private ItemStack getItemStack(String itemId) {
        net.minecraft.resources.ResourceLocation location = new net.minecraft.resources.ResourceLocation(itemId);
        net.minecraft.world.item.Item item = ForgeRegistries.ITEMS.getValue(location);
        return item == null ? ItemStack.EMPTY : new ItemStack(item, 1);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics);

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        int coins = ChaosEconomy.getCoins(player.getUUID());

        graphics.fill(guiLeft, guiTop, guiLeft + GUI_WIDTH, guiTop + GUI_HEIGHT, 0xC0000000);
        graphics.fill(guiLeft + 5, guiTop + 5, guiLeft + GUI_WIDTH - 5, guiTop + GUI_HEIGHT - 5, 0xFF111111);

        graphics.drawCenteredString(font, Component.literal("§6§lCHAOS SHOP"), guiLeft + GUI_WIDTH / 2, guiTop + 12, 0xFFFFFF);

        String coinText = "💰 " + coins + " coins";
        graphics.drawString(font, Component.literal("§6" + coinText), guiLeft + GUI_WIDTH - font.width(coinText) - 10, guiTop + 14, 0xFFFFFF);

        graphics.fill(guiLeft + 10, guiTop + 28, guiLeft + GUI_WIDTH - 10, guiTop + 29, 0x444444);

        int yOffset = 38;
        int slotHeight = 22;

        for (int i = 0; i < items.length; i++) {
            ShopItem shopItem = items[i];
            int y = guiTop + yOffset + (i * slotHeight);

            ItemStack icon = getItemStack(shopItem.itemId);

            if (mouseX >= guiLeft + 8 && mouseX < guiLeft + GUI_WIDTH - 8 && mouseY >= y && mouseY < y + slotHeight) {
                graphics.fill(guiLeft + 8, y, guiLeft + GUI_WIDTH - 8, y + slotHeight, 0x44FFAA00);
            }

            graphics.renderItem(icon, guiLeft + 12, y + 3);
            graphics.renderItemDecorations(font, icon, guiLeft + 12, y + 3);

            graphics.drawString(font, Component.literal(shopItem.name), guiLeft + 32, y + 5, 0xFFFFFF);

            String priceText = "§6" + shopItem.price + " coins";
            int priceWidth = font.width(priceText);
            graphics.drawString(font, Component.literal(priceText), guiLeft + GUI_WIDTH - priceWidth - 12, y + 6, 0xFFFFFF);

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


                    ItemStack bought = getItemStack(shopItem.itemId);
                    bought.setCount(1);


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
        String id;
        String name;
        int price;
        String itemId;

        ShopItem(String id, String name, int price, String itemId) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.itemId = itemId;
        }
    }
}