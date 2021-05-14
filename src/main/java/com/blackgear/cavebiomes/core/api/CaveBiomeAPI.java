package com.blackgear.cavebiomes.core.api;

import com.blackgear.cavebiomes.core.CaveConfig;
import com.blackgear.cavebiomes.core.registries.CaveBiomes;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.Layer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//<>

public class CaveBiomeAPI {
    private static Layer caveLayer;

    /**
     * Initializes the cave generation into the biome provider.
     *
     * @implNote This initialization calls the biome provider seed and the cave biome size.
     *
     * @param seed the biome provider seed
     * @param size the cave biome size
     *
     * @see com.blackgear.cavebiomes.mixin.OverworldBiomeProviderMixin#initialize(long, boolean, boolean, Registry, CallbackInfo)
     */
    public static void initializeCaveBiomes(long seed, int size) {
        caveLayer = CaveLayer.generateCaveLayers(seed, size);
    }

    /**
     * Injects the CaveBiome generation into a biomeProvider
     *
     * @implNote we don't make cave biomes spawn at y0 because otherwise entities and structures don't spawn.
     *
     * @param surfaceBiomes the generated surface biomes
     * @param biomeRegistry the biome registry given in the biome provider
     * @param xIn the {@link net.minecraft.world.biome.provider.BiomeProvider#getNoiseBiome(int, int, int)} x value
     * @param yIn the {@link net.minecraft.world.biome.provider.BiomeProvider#getNoiseBiome(int, int, int)} y value
     * @param zIn the {@link net.minecraft.world.biome.provider.BiomeProvider#getNoiseBiome(int, int, int)} z value
     *
     * @return the CaveBiomes injected into the biomeProvider
     * @see com.blackgear.cavebiomes.mixin.OverworldBiomeProviderMixin#getNoiseBiome(int, int, int)
     */
    public static Biome injectCaveBiomes(Biome surfaceBiomes, Registry<Biome> biomeRegistry, int xIn, int yIn, int zIn) {
        if (CaveConfig.hasCaveBiomes.get()) {
            if (yIn <= 12 && yIn >= 1) {
                return caveLayer.func_242936_a(biomeRegistry, xIn, zIn);
            }
        }
        return surfaceBiomes;
    }

    /**
     * Injects a CaveBiome into the biomeLayer
     *
     * @see #addDefaultCaves()
     *
     * @param biome the biome for injection
     */
    public static void addCaveBiome(Biome biome) {
        CaveLayer.biomes.add(biome);
    }

    /**
     * Injects the selected CaveBiomes into the biomeLayer
     *
     * @see #addCaveBiome(Biome)
     */
    public static void addDefaultCaves() {
        if (CaveConfig.hasDefaultCave.get()) {
            CaveBiomeAPI.addCaveBiome(CaveBiomes.CAVE.get());
        }
    }
}