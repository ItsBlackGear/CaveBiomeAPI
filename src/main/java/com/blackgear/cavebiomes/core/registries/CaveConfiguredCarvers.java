package com.blackgear.cavebiomes.core.registries;

import com.blackgear.cavebiomes.core.CaveBiome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;

//<>

public class CaveConfiguredCarvers {
    public static final ConfiguredCarver<ProbabilityConfig> NOISE_CARVER = registerConfiguredCarver("noise_carver", CaveCarvers.NOISE_CARVER.get().func_242761_a(new ProbabilityConfig(1F)));

    public static <C extends ICarverConfig, CC extends ConfiguredCarver<C>> CC registerConfiguredCarver(String key, CC configuredFeature) {
        ResourceLocation ID = new ResourceLocation(CaveBiome.MOD_ID, key);
        if (WorldGenRegistries.CONFIGURED_CARVER.keySet().contains(ID)) {
            throw new IllegalStateException("The Configured Carver " + key + "already exists in the registry");
        }

        Registry.register(WorldGenRegistries.CONFIGURED_CARVER, ID, configuredFeature);
        return configuredFeature;
    }
}