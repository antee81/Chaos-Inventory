package chaosinventory;

import chaosinventory.config.ChaosConfig;
import chaosinventory.config.HUDConfig;
import chaosinventory.data.DataManager;
import chaosinventory.sound.ModSounds;
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

        ModSounds.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        ChaosConfig.load();
        HUDConfig.load();
        DataManager.load();

        LOGGER.info("🌀 Chaos Inventory loaded!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("🌀 Chaos Inventory - Setup completed");
        ChaosRegistry.registerAll();
    }
}
