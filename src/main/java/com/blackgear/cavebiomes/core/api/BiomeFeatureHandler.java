package com.blackgear.cavebiomes.core.api;

import com.blackgear.cavebiomes.core.CaveBiomeConfig;
import com.blackgear.cavebiomes.core.registries.CaveBiomes;
import com.blackgear.cavebiomes.core.registries.CaveConfiguredCarvers;
import com.blackgear.cavebiomes.core.registries.CaveSurfaceBuilders;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber
public class BiomeFeatureHandler {
    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        GlobalBiomeManager manager = new GlobalBiomeManager(event);

        if (manager.matches(CaveBiomes.CAVE.get())) {
            this.generateDefaultCaveFeatures(manager);
        }

        if (CaveBiomeConfig.generateNoiseCarvers.get() && manager.canGenerate()) {
            event.getGeneration().getCarvers(GenerationStage.Carving.AIR).add(() -> CaveConfiguredCarvers.NOISE_CARVER);
        }
    }

    private void generateDefaultCaveFeatures(GlobalBiomeManager manager) {
        manager.generation().withSurfaceBuilder(new ConfiguredSurfaceBuilder<>(CaveSurfaceBuilders.DEFAULT_CAVE.get(), SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG));
    }
}