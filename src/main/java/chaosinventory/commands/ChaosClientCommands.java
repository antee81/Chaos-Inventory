package chaosinventory.commands;

import chaosinventory.gui.ChaosShopScreen;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = chaosinventory.ChaosInventory.MODID, value = Dist.CLIENT)
public class ChaosClientCommands {

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("chaos")
                .then(Commands.literal("shop")
                        .executes(context -> {
                            Minecraft.getInstance().setScreen(new ChaosShopScreen());
                            return 1;
                        })
                )
        );
    }
}