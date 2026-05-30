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

                // /chaos reload
                .then(Commands.literal("reload")
                        .requires(source -> source.hasPermission(1))
                        .executes(context -> {
                            ChaosConfig.load();
                            context.getSource().sendSystemMessage(Component.literal("\u00A7a\u2705 Config reloaded successfully!"));
                            return 1;
                        })
                )

                // /chaos timer
                .then(Commands.literal("timer")
                        .requires(source -> source.hasPermission(1))
                        .then(Commands.argument("seconds", IntegerArgumentType.integer(10, 7200))
                                .executes(context -> {
                                    int seconds = IntegerArgumentType.getInteger(context, "seconds");
                                    ChaosConfig.setChaosDurationSeconds(seconds);
                                    context.getSource().sendSystemMessage(Component.literal("\u00A7a\u23F0 Timer set to \u00A7e" + seconds + "\u00A7a seconds!"));
                                    return 1;
                                })
                        )
                )

                // /chaos trigger
                .then(Commands.literal("trigger")
                        .requires(source -> source.hasPermission(1))
                        .then(Commands.argument("event", StringArgumentType.string())
                                .executes(context -> {
                                    String eventName = StringArgumentType.getString(context, "event");
                                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                        boolean found = ChaosRegistry.triggerEventByName(player, eventName);
                                        if (found) {
                                            context.getSource().sendSystemMessage(Component.literal("\u00A7a\uD83D\uDCA5 Event \u00A7e" + eventName + "\u00A7a triggered!"));
                                        } else {
                                            context.getSource().sendSystemMessage(Component.literal("\u00A7c\u274C Evento \u00A7e" + eventName + "\u00A7c not found!"));
                                        }
                                    }
                                    return 1;
                                })
                        )
                )

                // /chaos toggle
                .then(Commands.literal("toggle")
                        .requires(source -> source.hasPermission(1))
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                boolean current = ChaosTimer.isGlobalEnabled();
                                ChaosTimer.setGlobalEnabled(!current);
                                if (!current) {
                                    context.getSource().sendSystemMessage(Component.literal("\u00A7a\uD83C\uDF00 Chaos \u00A7aENABLED!"));
                                } else {
                                    context.getSource().sendSystemMessage(Component.literal("\u00A7c\uD83C\uDF00 Chaos \u00A7cDISABLED!"));
                                }
                            }
                            return 1;
                        })
                )

                // /chaos list
                .then(Commands.literal("list")
                        .executes(context -> {
                            context.getSource().sendSystemMessage(Component.literal("\u00A7e\uD83D\uDCCB \u00A7eEvent available: \u00A7a" + ChaosRegistry.getEventCount()));
                            context.getSource().sendSystemMessage(Component.literal("\u00A77Use \u00A7e/chaos trigger <name> \u00A77to enable an event!"));
                            return 1;
                        })
                )

                // /chaos stats
                .then(Commands.literal("stats")
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                int xp = ChaosStats.getPlayerXP(player);
                                int level = ChaosStats.getPlayerLevel(player);
                                int totalEvents = ChaosStats.getPlayerTotalEvents(player);
                                int xpToNext = ChaosStats.getXPToNextLevel(player);
                                int progress = ChaosStats.getProgressPercent(player);

                                context.getSource().sendSystemMessage(Component.translatable("chaos.command.stats"));
                                context.getSource().sendSystemMessage(Component.translatable("chaos.command.level", progress));
                                context.getSource().sendSystemMessage(Component.translatable("chaos.command.xp", xp));
                                context.getSource().sendSystemMessage(Component.translatable("chaos.command.events", totalEvents));
                                context.getSource().sendSystemMessage(Component.translatable("chaos.command.xp_next", xpToNext));

                                context.getSource().sendSystemMessage(Component.translatable("chaos.command.frequent"));
                                var counts = ChaosStats.getEventCounts(player);
                                counts.entrySet().stream()
                                        .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                                        .limit(3)
                                        .forEach(e -> context.getSource().sendSystemMessage(
                                                Component.literal("\u00A78  - \u00A7f" + e.getKey() + "\u00A77: \u00A7e" + e.getValue())
                                        ));
                            }
                            return 1;
                        })
                )

                // /chaos rewards
                .then(Commands.literal("rewards")
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                int level = ChaosStats.getPlayerLevel(player);
                                var unlocked = ChaosStats.getUnlockedRewards(player);

                                context.getSource().sendSystemMessage(Component.literal("\u00A76\u00A7l\uD83C\uDF81 CHAOS REWARD"));
                                context.getSource().sendSystemMessage(Component.literal("\u00A77Level : \u00A7e" + level));
                                context.getSource().sendSystemMessage(Component.literal(""));
                                context.getSource().sendSystemMessage(Component.literal("\u00A77\u00A7lUnlocked:"));

                                if (unlocked.isEmpty()) {
                                    context.getSource().sendSystemMessage(Component.literal("\u00A78  No rewards unlocked yet"));
                                } else {
                                    for (String rewardLevel : unlocked) {
                                        context.getSource().sendSystemMessage(Component.literal("\u00A7a  \u2713 " + ChaosStats.getRewardDescription(Integer.parseInt(rewardLevel))));
                                    }
                                }

                                context.getSource().sendSystemMessage(Component.literal(""));
                                context.getSource().sendSystemMessage(Component.literal("\u00A77\u00A7lNext rewards:"));

                                int nextMilestone = ((level / 5) + 1) * 5;
                                while (nextMilestone <= 100 && ChaosStats.getRewardDescription(nextMilestone).equals("No Rewards")) {
                                    nextMilestone += 5;
                                }
                                if (nextMilestone <= 100) {
                                    context.getSource().sendSystemMessage(Component.literal("\u00A78  Level " + nextMilestone + ": \u00A77" + ChaosStats.getRewardDescription(nextMilestone)));
                                }
                            }
                            return 1;
                        })
                )
                .then(Commands.literal("color")
                        .requires(source -> source.hasPermission(0))
                        .then(Commands.argument("color", StringArgumentType.string())
                                .suggests((context, builder) -> {
                                    for (String c : ChaosConfig.COLOR_CODES.keySet()) builder.suggest(c);
                                    return builder.buildFuture();
                                })
                                .executes(context -> {
                                    String color = StringArgumentType.getString(context, "color").toUpperCase();
                                    if (!ChaosConfig.COLOR_CODES.containsKey(color)) {
                                        context.getSource().sendSystemMessage(Component.literal("\u00A7cInvalid color"));
                                        return 0;
                                    }
                                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                        String uuid = player.getStringUUID();
                                        ChaosConfig.setPlayerTimerColor(uuid, color);
                                        ChaosConfig.save();
                                        context.getSource().sendSystemMessage(Component.literal("\u00A7a\u2705 Timer color set to \u00A7" + getColorCode(color) + color));
                                    } else {
                                        context.getSource().sendSystemMessage(Component.literal("\u00A7cOnly players can use this"));
                                    }
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("language")
                        .requires(source -> source.hasPermission(0))
                        .then(Commands.literal("en_us")
                                .executes(context -> {
                                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                        ChaosConfig.setCurrentLanguage("en_us");
                                        context.getSource().sendSystemMessage(Component.translatable("chaos.language.changed", "English"));
                                    }
                                    return 1;
                                })
                        )
                        .then(Commands.literal("it_it")
                                .executes(context -> {
                                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                        ChaosConfig.setCurrentLanguage("it_it");
                                        context.getSource().sendSystemMessage(Component.translatable("chaos.language.changed", "Italiano"));
                                    }
                                    return 1;
                                })
                        )
                        .then(Commands.literal("es_es")
                                .executes(context -> {
                                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                        ChaosConfig.setCurrentLanguage("es_es");
                                        context.getSource().sendSystemMessage(Component.translatable("chaos.language.changed", "Español"));
                                    }
                                    return 1;
                                })
                        )
                        .then(Commands.literal("fr_fr")
                                .executes(context -> {
                                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                        ChaosConfig.setCurrentLanguage("fr_fr");
                                        context.getSource().sendSystemMessage(Component.translatable("chaos.language.changed", "Français"));
                                    }
                                    return 1;
                                })
                        )
                        .then(Commands.literal("de_de")
                                .executes(context -> {
                                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                        ChaosConfig.setCurrentLanguage("de_de");
                                        context.getSource().sendSystemMessage(Component.translatable("chaos.language.changed", "Deutsch"));
                                    }
                                    return 1;
                                })
                        )
                        .executes(context -> {
                            String current = ChaosConfig.getCurrentLanguage();
                            context.getSource().sendSystemMessage(Component.translatable("chaos.language.current", current));
                            context.getSource().sendSystemMessage(Component.literal("§7Available: en_us, it_it, es_es, fr_fr, de_de"));
                            context.getSource().sendSystemMessage(Component.literal("§7Use §e/chaos language <code> §7to change"));
                            return 1;
                        })
                )
        );
    }


    private static String getColorCode(String color) {
        switch (color) {
            case "WHITE":
                return "f";
            case "RED":
                return "c";
            case "GREEN":
                return "a";
            case "BLUE":
                return "9";
            case "YELLOW":
                return "e";
            case "GOLD":
                return "6";
            case "AQUA":
                return "b";
            case "LIGHT_PURPLE":
                return "d";
            case "DARK_RED":
                return "4";
            case "DARK_GREEN":
                return "2";
            case "DARK_BLUE":
                return "1";
            case "GRAY":
                return "7";
            case "DARK_GRAY":
                return "8";
            default:
                return "f";
        }
    }
}


