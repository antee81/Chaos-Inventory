package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ChatSpamEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Chat Spam";
    }

    @Override
    public int getWeight() {
        return 20;
    }

    @Override
    public boolean isMultiplayerOnly() {
        return true;
    }

    @Override
    public void execute(ServerPlayer player) {
        for (int i = 0; i < 5; i++) {
            player.sendSystemMessage(Component.literal(ChatFormatting.RED + "CHAOS! " + ChatFormatting.GOLD + "CHAOS! " + ChatFormatting.DARK_RED + "CHAOS!"));
        }
        player.sendSystemMessage(Component.literal("§4§l\uD83C\uDF00 CHAOS IS HERE! \uD83C\uDF00"));
    }
}
