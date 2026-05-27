package chaosinventory.commands;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import chaosinventory.ChaosInventory;

@Mod.EventBusSubscriber(modid = ChaosInventory.MODID)
public class CommandHandler {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ChaosCommands.register(event.getDispatcher());
        ChaosInventory.LOGGER.info("✅ Chaos Commands registered!");
    }
}
