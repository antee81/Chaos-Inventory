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
                            context.getSource().sendSystemMessage(Component.literal("\u00A7a\u2705 Configurazione ricaricata con successo!"));
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
                                    context.getSource().sendSystemMessage(Component.literal("\u00A7a\u23F0 Timer impostato a \u00A7e" + seconds + "\u00A7a secondi!"));
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
                                            context.getSource().sendSystemMessage(Component.literal("\u00A7a\uD83D\uDCA5 Evento \u00A7e" + eventName + "\u00A7a scatenato!"));
                                        } else {
                                            context.getSource().sendSystemMessage(Component.literal("\u00A7c\u274C Evento \u00A7e" + eventName + "\u00A7c non trovato!"));
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
                                    context.getSource().sendSystemMessage(Component.literal("\u00A7a\u25B6\uFE0F Caos \u00A7aATTIVATO!"));
                                } else {
                                    context.getSource().sendSystemMessage(Component.literal("\u00A7c\u23F8\uFE0F Caos \u00A7cDISATTIVATO!"));
                                }
                            }
                            return 1;
                        })
                )

                // /chaos list
                .then(Commands.literal("list")
                        .executes(context -> {
                            context.getSource().sendSystemMessage(Component.literal("\u00A7e\uD83D\uDCCB \u00A7eEventi disponibili: \u00A7a" + ChaosRegistry.getEventCount()));
                            context.getSource().sendSystemMessage(Component.literal("\u00A77Usa \u00A7e/chaos trigger <nome> \u00A77per attivare un evento!"));
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

                                context.getSource().sendSystemMessage(Component.literal("\u00A76\u00A7l\uD83D\uDCCA STATISTICHE CHAOS"));
                                context.getSource().sendSystemMessage(Component.literal("\u00A77Livello: \u00A7e" + level + " \u00A77(\u00A7f" + progress + "%\u00A77 al prossimo)"));
                                context.getSource().sendSystemMessage(Component.literal("\u00A77XP totali: \u00A7e" + xp));
                                context.getSource().sendSystemMessage(Component.literal("\u00A77Eventi subiti: \u00A7e" + totalEvents));
                                context.getSource().sendSystemMessage(Component.literal("\u00A77XP al prossimo livello: \u00A7e" + xpToNext));

                                var counts = ChaosStats.getEventCounts(player);
                                context.getSource().sendSystemMessage(Component.literal("\u00A77Eventi pi\u00F9 frequenti:"));
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

                                context.getSource().sendSystemMessage(Component.literal("\u00A76\u00A7l\uD83C\uDF81 RICOMPENSE CHAOS"));
                                context.getSource().sendSystemMessage(Component.literal("\u00A77Livello attuale: \u00A7e" + level));
                                context.getSource().sendSystemMessage(Component.literal(""));
                                context.getSource().sendSystemMessage(Component.literal("\u00A77\u00A7lSbloccate:"));

                                if (unlocked.isEmpty()) {
                                    context.getSource().sendSystemMessage(Component.literal("\u00A78  Nessuna ricompensa ancora sbloccata"));
                                } else {
                                    for (String rewardLevel : unlocked) {
                                        context.getSource().sendSystemMessage(Component.literal("\u00A7a  \u2713 " + ChaosStats.getRewardDescription(Integer.parseInt(rewardLevel))));
                                    }
                                }

                                context.getSource().sendSystemMessage(Component.literal(""));
                                context.getSource().sendSystemMessage(Component.literal("\u00A77\u00A7lProssime ricompense:"));

                                int nextMilestone = ((level / 5) + 1) * 5;
                                while (nextMilestone <= 100 && ChaosStats.getRewardDescription(nextMilestone).equals("Nessuna ricompensa")) {
                                    nextMilestone += 5;
                                }
                                if (nextMilestone <= 100) {
                                    context.getSource().sendSystemMessage(Component.literal("\u00A78  Livello " + nextMilestone + ": \u00A77" + ChaosStats.getRewardDescription(nextMilestone)));
                                }
                            }
                            return 1;
                        })
                )
        );
    }
}
