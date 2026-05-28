package chaosinventory.commands;

import chaosinventory.ChaosInventory;
import chaosinventory.ChaosRegistry;
import chaosinventory.ChaosTimer;
import chaosinventory.config.ChaosConfig;
import chaosinventory.stats.ChaosStats;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ChaosCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("chaos")
                .requires(source -> source.hasPermission(0))
                .then(Commands.literal("reload")
                        .requires(source -> source.hasPermission(1))
                        .executes(context -> {
                            ChaosConfig.load();
                            context.getSource().sendSystemMessage(Component.literal("[CHAOS] Config reloaded!"));
                            return 1;
                        })
                )
                .then(Commands.literal("timer")
                        .requires(source -> source.hasPermission(1))
                        .then(Commands.argument("seconds", IntegerArgumentType.integer(10, 7200))
                                .executes(context -> {
                                    int seconds = IntegerArgumentType.getInteger(context, "seconds");
                                    ChaosConfig.setChaosDurationSeconds(seconds);
                                    context.getSource().sendSystemMessage(Component.literal("[CHAOS] Timer set to " + seconds + " seconds!"));
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("trigger")
                        .requires(source -> source.hasPermission(1))
                        .then(Commands.argument("event", StringArgumentType.string())
                                .executes(context -> {
                                    String eventName = StringArgumentType.getString(context, "event");
                                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                        boolean found = ChaosRegistry.triggerEventByName(player, eventName);
                                        if (found) {
                                            context.getSource().sendSystemMessage(Component.literal("[CHAOS] Event " + eventName + " triggered!"));
                                        } else {
                                            context.getSource().sendSystemMessage(Component.literal("[CHAOS] Event " + eventName + " not found!"));
                                        }
                                    }
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("toggle")
                        .requires(source -> source.hasPermission(1))
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                boolean current = ChaosTimer.isGlobalEnabled();
                                ChaosTimer.setGlobalEnabled(!current);
                                String status = !current ? "ENABLED" : "DISABLED";
                                context.getSource().sendSystemMessage(Component.literal("[CHAOS] Chaos " + status + "!"));
                            }
                            return 1;
                        })
                )
                .then(Commands.literal("list")
                        .executes(context -> {
                            context.getSource().sendSystemMessage(Component.literal("[CHAOS] Available events: " + ChaosRegistry.getEventCount()));
                            context.getSource().sendSystemMessage(Component.literal("Use /chaos trigger <name> to activate an event"));
                            return 1;
                        })
                )
                .then(Commands.literal("stats")
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                int xp = ChaosStats.getPlayerXP(player);
                                int level = ChaosStats.getPlayerLevel(player);
                                int totalEvents = ChaosStats.getPlayerTotalEvents(player);
                                int xpToNext = ChaosStats.getXPToNextLevel(player);
                                int progress = ChaosStats.getProgressPercent(player);

                                context.getSource().sendSystemMessage(Component.literal("=== CHAOS STATISTICS ==="));
                                context.getSource().sendSystemMessage(Component.literal("Level: " + level + " (" + progress + "% to next)"));
                                context.getSource().sendSystemMessage(Component.literal("Total XP: " + xp));
                                context.getSource().sendSystemMessage(Component.literal("Events survived: " + totalEvents));
                                context.getSource().sendSystemMessage(Component.literal("XP to next level: " + xpToNext));

                                var counts = ChaosStats.getEventCounts(player);
                                context.getSource().sendSystemMessage(Component.literal("Most frequent events:"));
                                counts.entrySet().stream()
                                        .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                                        .limit(3)
                                        .forEach(e -> context.getSource().sendSystemMessage(
                                                Component.literal("  - " + e.getKey() + ": " + e.getValue())
                                        ));
                            }
                            return 1;
                        })
                )
                .then(Commands.literal("rewards")
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                int level = ChaosStats.getPlayerLevel(player);
                                var unlocked = ChaosStats.getUnlockedRewards(player);

                                context.getSource().sendSystemMessage(Component.literal("=== CHAOS REWARDS ==="));
                                context.getSource().sendSystemMessage(Component.literal("Current level: " + level));
                                context.getSource().sendSystemMessage(Component.literal(""));
                                context.getSource().sendSystemMessage(Component.literal("Unlocked:"));

                                if (unlocked.isEmpty()) {
                                    context.getSource().sendSystemMessage(Component.literal("  None yet"));
                                } else {
                                    for (String rewardLevel : unlocked) {
                                        context.getSource().sendSystemMessage(Component.literal("  - " + ChaosStats.getRewardDescription(Integer.parseInt(rewardLevel))));
                                    }
                                }

                                context.getSource().sendSystemMessage(Component.literal(""));
                                context.getSource().sendSystemMessage(Component.literal("Next rewards:"));

                                int nextMilestone = ((level / 5) + 1) * 5;
                                while (nextMilestone <= 100 && ChaosStats.getRewardDescription(nextMilestone).equals("Nessuna ricompensa")) {
                                    nextMilestone += 5;
                                }
                                if (nextMilestone <= 100) {
                                    context.getSource().sendSystemMessage(Component.literal("  Level " + nextMilestone + ": " + ChaosStats.getRewardDescription(nextMilestone)));
                                }
                            }
                            return 1;
                        })
                )
        );
    }
}
