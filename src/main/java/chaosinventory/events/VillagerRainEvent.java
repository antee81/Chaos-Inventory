package chaosinventory.events;

import chaosinventory.ChaosEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;

public class VillagerRainEvent implements ChaosEvent {
    @Override
    public String getName() {
        return "Villager Rain";
    }

    @Override
    public int getWeight() {
        return 6;
    }

    @Override
    public void execute(ServerPlayer player) {
        for (int i = 0; i < 15; i++) {
            Villager villager = EntityType.VILLAGER.create(player.serverLevel());
            if (villager != null) {
                double x = player.getX() + (Math.random() - 0.5) * 10;
                double z = player.getZ() + (Math.random() - 0.5) * 10;
                villager.moveTo(x, player.getY() + 15, z);
                villager.setNoGravity(false);
                player.serverLevel().addFreshEntity(villager);
            }
        }
        player.sendSystemMessage(Component.literal("§6\uD83E\uDDD9\u200D♂\uFE0F Villager Rain! HMMMM!"));
    }
}
