package chaosinventory.commands;

import chaosinventory.ChaosInventory;
import chaosinventory.ChaosRegistry;
import chaosinventory.ChaosTimer;
import chaosinventory.config.ChaosConfig;
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
                .requires(source -> source.hasPermission(1)) // OP necessario
                .then(Commands.literal("reload")
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
                        .executes(context -> {
                            context.getSource().sendSystemMessage(Component.literal("§6📋 Chaos Event available: §e" + ChaosRegistry.getEventCount()));
                            context.getSource().sendSystemMessage(Component.literal("§7Usa §f/chaos trigger <name> §7 to trigger an event"));
                            return 1;
                        })
                )
        );
    }
}
