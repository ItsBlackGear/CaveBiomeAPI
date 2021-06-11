package com.blackgear.cavebiomes.core.api;

import com.blackgear.cavebiomes.core.CaveConfig;
import com.blackgear.cavebiomes.core.registries.CaveConfiguredCarvers;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber
public class GlobalBiomeFeatures {
    public boolean shouldGenerate(BiomeLoadingEvent event) {
        return event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND && event.getCategory() != Biome.Category.OCEAN;
    }

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        if (event.getName() == null) return;

        if (CaveConfig.HAS_NOISE_CARVERS.get() && shouldGenerate(event)) {
            event.getGeneration().getCarvers(GenerationStage.Carving.AIR).add(() -> CaveConfiguredCarvers.NOISE_CARVER);
        }
    }
}