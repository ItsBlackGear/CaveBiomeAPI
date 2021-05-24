package com.blackgear.cavebiomes.common.events;

import com.blackgear.cavebiomes.core.Caves;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
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
            if (mapping.key.getNamespace().equals("bgcore")) {
                mapping.remap(ForgeRegistries.BIOMES.getValue(new ResourceLocation(Caves.MODID, mapping.key.getPath())));
            }
        }
    }
}