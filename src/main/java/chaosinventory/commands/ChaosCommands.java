package chaosinventory.commands;

import chaosinventory.stats.ChaosStats;
import chaosinventory.ChaosEvent;
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
                .requires(source -> source.hasPermission(0)) // NO OP
                .then(Commands.literal("reload")
                        .requires(source -> source.hasPermission(1))
                        .executes(context -> {
                            ChaosConfig.load();
                            context.getSource().sendSystemMessage(Component.literal("§a✅ ChaosConfig reloaded!"));
                            ChaosInventory.LOGGER.info("ChaosConfig reloaded by command");
                            return 1;
                        })
                )
                .then(Commands.literal("timer")
                        .then(Commands.argument("seconds", IntegerArgumentType.integer(10, 7200))
                                .executes(context -> {
                                    int seconds = IntegerArgumentType.getInteger(context, "seconds");
                                    ChaosConfig.setChaosDurationSeconds(seconds);
                                    context.getSource().sendSystemMessage(Component.literal("§a⏰ Timer set to " + seconds + " seconds!"));
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("trigger")
                        .then(Commands.argument("event", StringArgumentType.string())
                                .executes(context -> {
                                    String eventName = StringArgumentType.getString(context, "event");
                                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                        boolean found = ChaosRegistry.triggerEventByName(player, eventName);
                                        if (found) {
                                            context.getSource().sendSystemMessage(Component.literal("§a🌀 Event §e" + eventName + " §a triggered!"));
                                        } else {
                                            context.getSource().sendSystemMessage(Component.literal("§c❌ Event §e" + eventName + " §cnot found!"));
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
                                String status = !current ? "§aactived" : "§cdisabled";
                                context.getSource().sendSystemMessage(Component.literal("§e🌀 Chaos " + status + "!"));
                            }
                            return 1;
                        })
                )
                .then(Commands.literal("list")
                        .requires(source -> source.hasPermission(0))
                        .executes(context -> {
                            context.getSource().sendSystemMessage(Component.literal("§6📋 Chaos Event available: §e" + ChaosRegistry.getEventCount()));
                            context.getSource().sendSystemMessage(Component.literal("§7Usa §f/chaos trigger <name> §7 to trigger an event"));
                            return 1;
                        })
                )
                .then(Commands.literal("stats")
                        .requires(source -> source.hasPermission(0))
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                int xp = ChaosStats.getPlayerXP(player);
                                int level = ChaosStats.getPlayerLevel(player);
                                int totalEvents = ChaosStats.getPlayerTotalEvents(player);
                                int xpToNext = ChaosStats.getXPToNextLevel(player);
                                int progress = ChaosStats.getProgressPercent(player);

                                context.getSource().sendSystemMessage(Component.literal("§6§l\uD83D\uDCCA CHAOS STATISTICS"));
                                context.getSource().sendSystemMessage(Component.literal("§7Level: §e" + level + " §7(§f" + progress + "%§7 to the next)"));
                                context.getSource().sendSystemMessage(Component.literal("§7Total XP: §e" + xp));
                                context.getSource().sendSystemMessage(Component.literal("§7Event you went through: §e" + totalEvents));
                                context.getSource().sendSystemMessage(Component.literal("§7XP to the next level: §e" + xpToNext));

                                var counts = ChaosStats.getEventCounts(player);
                                context.getSource().sendSystemMessage(Component.literal("§7Most frequent events:"));
                                counts.entrySet().stream()
                                        .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                                        .limit(3)
                                        .forEach(e -> context.getSource().sendSystemMessage(
                                                Component.literal("  §8- §f" + e.getKey() + "§7: §e" + e.getValue())
                                        ));
                            }
                            return 1;
                        })
                )
                .then(Commands.literal("rewards")
                        .requires(source -> source.hasPermission(0))
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                int level = ChaosStats.getPlayerLevel(player);
                                var unlocked = ChaosStats.getUnlockedRewards(player);

                                context.getSource().sendSystemMessage(Component.literal("§6§l\uD83C\uDF81 CHAOS REWARDS"));
                                context.getSource().sendSystemMessage(Component.literal("§7Current Level: §e" + level));
                                context.getSource().sendSystemMessage(Component.literal(""));
                                context.getSource().sendSystemMessage(Component.literal("§7§lUnlocked:"));

                                if (unlocked.isEmpty()) {
                                    context.getSource().sendSystemMessage(Component.literal("  §8No rewards unlocked yet.."));
                                } else {
                                    for (String rewardLevel : unlocked) {
                                        context.getSource().sendSystemMessage(Component.literal("  §a✓" + ChaosStats.getRewardDescription(Integer.parseInt(rewardLevel))));
                                    }
                                }

                                context.getSource().sendSystemMessage(Component.literal(""));
                                context.getSource().sendSystemMessage(Component.literal("§7§lNext reward:"));

                                int nextMilestone = ((level / 5) + 1) * 5;
                                while (nextMilestone <= 100 && ChaosStats.getRewardDescription(nextMilestone).equals("No reward")) {
                                    nextMilestone += 5;
                                }
                                if (nextMilestone <= 100) {
                                    context.getSource().sendSystemMessage(Component.literal("  §8Level " + nextMilestone + ": §7" + ChaosStats.getRewardDescription(nextMilestone)));
                                }
                            }
                            return 1;
                        })
                )
        );
    }
}
