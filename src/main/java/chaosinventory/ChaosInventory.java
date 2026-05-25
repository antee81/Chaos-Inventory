package chaosinventory;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ChaosInventory.MODID)
public class ChaosInventory {
    public static final String MODID = "chaosinventory";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChaosInventory(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Registra eventi
        modEventBus.addListener(this::commonSetup);

        // Registra il bus di Forge
        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.info("🌀 Chaos Inventory loaded!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("🌀 Chaos Inventory - Setup completed");
        ChaosRegistry.registerAll();
    }
}
