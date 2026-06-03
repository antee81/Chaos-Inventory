package chaosinventory.advancements;

import chaosinventory.ChaosInventory;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ChaosInventory.MODID)
public class AdvancementHelper {

    private static final Map<String, Advancement> advancements = new HashMap<>();
    private static final Map<UUID, Map<String, Boolean>> awardedAchievements = new HashMap<>();

    public static void init() {
        registerAdvancement("event_10", "§eEvent Survivor", "§7Survive 10 chaos events",
                new ItemStack(Items.DIAMOND), FrameType.TASK);
        registerAdvancement("event_50", "§6Event Veteran", "§7Survive 50 chaos events",
                new ItemStack(Items.GOLD_BLOCK), FrameType.TASK);
        registerAdvancement("event_100", "§5Chaos Legend", "§7Survive 100 chaos events",
                new ItemStack(Items.NETHER_STAR), FrameType.CHALLENGE);

        registerAdvancement("level_10", "§aRising Star", "§7Reach level 10",
                new ItemStack(Items.EXPERIENCE_BOTTLE), FrameType.TASK);
        registerAdvancement("level_25", "§bChaos Adept", "§7Reach level 25",
                new ItemStack(Items.ENCHANTING_TABLE), FrameType.GOAL);
        registerAdvancement("level_50", "§dChaos Master", "§7Reach level 50",
                new ItemStack(Items.DRAGON_HEAD), FrameType.GOAL);
        registerAdvancement("level_100", "§4§lChaos God", "§7Reach level 100",
                new ItemStack(Items.BEACON), FrameType.CHALLENGE);

        registerAdvancement("xp_1000", "§XP Novice", "§7Earn 1000 XP",
                new ItemStack(Items.EXPERIENCE_BOTTLE), FrameType.TASK);
        registerAdvancement("xp_5000", "§bXP Expert", "§7Earn 5000 XP",
                new ItemStack(Items.ENCHANTED_BOOK), FrameType.GOAL);
        registerAdvancement("xp_10000", "§dXP Master", "§7Earn 10000 XP",
                new ItemStack(Items.BEACON), FrameType.CHALLENGE);

        registerAdvancement("coins_500", "§6Rich", "§7Accumulate 500 coins",
                new ItemStack(Items.GOLD_INGOT), FrameType.TASK);
        registerAdvancement("coins_2000", "§eMillionaire", "§7Accumulate 2000 coins",
                new ItemStack(Items.GOLD_BLOCK), FrameType.GOAL);
        registerAdvancement("coins_10000", "§6§lChaos Tycoon", "§7Accumulate 10000 coins",
                new ItemStack(Items.EMERALD_BLOCK), FrameType.CHALLENGE);

        registerAdvancement("quest_5", "§aQuest Starter", "§7Complete 5 quests",
                new ItemStack(Items.BOOK), FrameType.TASK);
        registerAdvancement("quest_20", "§bQuest Hero", "§7Complete 20 quests",
                new ItemStack(Items.WRITTEN_BOOK), FrameType.GOAL);

        registerAdvancement("teleport_10", "§5Lost Wanderer", "§7Get teleported 10 times",
                new ItemStack(Items.ENDER_PEARL), FrameType.TASK);

    }
}
