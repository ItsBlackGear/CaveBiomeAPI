package com.blackgear.cavebiomes.common.events;

import com.blackgear.cavebiomes.core.CaveBiome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

//<>

@Mod.EventBusSubscriber
public class RemapEvent {
    @SubscribeEvent
    public void updateBiomeID(RegistryEvent.MissingMappings<Biome> event) {
        for (RegistryEvent.MissingMappings.Mapping<Biome> mapping : event.getAllMappings()) {
            if (mapping.key.getNamespace().equals("bgcore") || mapping.key.getNamespace().equals("cavebiomes")) {
                mapping.remap(ForgeRegistries.BIOMES.getValue(new ResourceLocation(CaveBiome.MOD_ID, mapping.key.getPath())));
            }
        }
    }
}