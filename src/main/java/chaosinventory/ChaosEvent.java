package chaosinventory;

import net.minecraft.server.level.ServerPlayer;

public interface ChaosEvent {
    String getName();
    int getWeight();
    void execute(ServerPlayer player);

    default boolean isMultiplayerOnly() {
        return false;
    }

    default String getEventType() {
        return "COMMON";
    }
}
