package chaosinventory;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ChaosInventory.MODID)
public class ChaosInventory
{
    public static final String MODID = "chaosinventory";
    public static final Logger LOGGER = LogUtils.getLogger();
    public ChaosInventory(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("\uD83C\uDF00 Chaos Inventory loaded!");
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("\uD83C\uDF00 Chaos Inventory - Setup completed");
        ChaosRegistry.registerAll();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("\uD83C\uDF00 Chaos's waking up...");
    }
}