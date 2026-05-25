package chaosinventory.sound;

import chaosinventory.ChaosInventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ChaosInventory.MODID);

    public static final RegistryObject<SoundEvent> CHAOS_GOOD = registerSoundEvent("chaos_good");
    public static final RegistryObject<SoundEvent> CHAOS_BAD = registerSoundEvent("chaos_bad");
    public static final RegistryObject<SoundEvent> CHAOS_TROLL = registerSoundEvent("chaos_troll");
    public static final RegistryObject<SoundEvent> CHAOS_EPIC = registerSoundEvent("chaos_epic");
    public static final RegistryObject<SoundEvent> CHAOS_EXPLOSION = registerSoundEvent("chaos_explosion");
    public static final RegistryObject<SoundEvent> CHAOS_TELEPORT = registerSoundEvent("chaos_teleport");
    public static final RegistryObject<SoundEvent> CHAOS_LIGHTNING = registerSoundEvent("chaos_lightning");
    public static final RegistryObject<SoundEvent> CHAOS_RAIN = registerSoundEvent("chaos_rain");  // ← corretto!

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(
                new ResourceLocation(ChaosInventory.MODID, name)  // ← corretto!
        ));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
        ChaosInventory.LOGGER.info("🔊 Sounds registered!");
    }
}