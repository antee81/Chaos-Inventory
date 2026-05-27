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

                // /chaos stats
                .then(Commands.literal("stats")
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                int xp = ChaosStats.getPlayerXP(player);
                                int level = ChaosStats.getPlayerLevel(player);
                                int totalEvents = ChaosStats.getPlayerTotalEvents(player);
                                int progress = ChaosStats.getProgressPercent(player);

                                context.getSource().sendSystemMessage(Component.literal("§6§l📊 CHAOS STATISTICS"));
                                context.getSource().sendSystemMessage(Component.literal("§7Level: §e" + level + " §7(§f" + progress + "%§7 to next)"));
                                context.getSource().sendSystemMessage(Component.literal("§7Total XP: §e" + xp));
                                context.getSource().sendSystemMessage(Component.literal("§7Events survived: §e" + totalEvents));
                            }
                            return 1;
                        })
                )

                // /chaos rewards
                .then(Commands.literal("rewards")
                        .executes(context -> {
                            if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                int level = ChaosStats.getPlayerLevel(player);
                                context.getSource().sendSystemMessage(Component.literal("§6§l🎁 CHAOS REWARDS"));
                                context.getSource().sendSystemMessage(Component.literal("§7Current level: §e" + level));
                            }
                            return 1;
                        })
                )

                // /chaos list
                .then(Commands.literal("list")
                        .executes(context -> {
                            context.getSource().sendSystemMessage(Component.literal("§6📋 Available Chaos events: §e" + ChaosRegistry.getEventCount()));
                            return 1;
                        })
                )
        );

        // OP ONLY COMMANDS
        dispatcher.register(Commands.literal("chaos")
                .requires(source -> source.hasPermission(2))

                .then(Commands.literal("reload")
                        .requires(source -> source.hasPermission(1))
                        .executes(context -> {
                            ChaosConfig.load();
                            context.getSource().sendSystemMessage(Component.literal("§a✅ ChaosConfig reloaded!"));
                            return 1;
                        })
                )

                .then(Commands.literal("timer")
                        .requires(source -> source.hasPermission(1))
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
                        .requires(source -> source.hasPermission(1))
                        .then(Commands.argument("event", StringArgumentType.string())
                                .executes(context -> {
                                    String eventName = StringArgumentType.getString(context, "event");
                                    if (context.getSource().getEntity() instanceof ServerPlayer player) {
                                        boolean found = ChaosRegistry.triggerEventByName(player, eventName);
                                        if (found) {
                                            context.getSource().sendSystemMessage(Component.literal("§a🌀 Event §e" + eventName + " §atriggered!"));
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
                            boolean current = ChaosTimer.isGlobalEnabled();
                            ChaosTimer.setGlobalEnabled(!current);
                            context.getSource().sendSystemMessage(Component.literal("§e🌀 Chaos " + (!current ? "§aenabled" : "§cdisabled") + "!"));
                            return 1;
                        })
                )
        );
    }
}
