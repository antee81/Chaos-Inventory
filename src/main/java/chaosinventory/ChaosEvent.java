package chaosinventory;

import net.minecraft.server.level.ServerPlayer;

public interface ChaosEvent {
    String getName();
    int getWeight();
    void execute(ServerPlayer player);
}
