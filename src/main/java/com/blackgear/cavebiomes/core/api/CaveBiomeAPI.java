package com.blackgear.cavebiomes.core.api;

import com.blackgear.cavebiomes.core.CaveConfig;
import com.blackgear.cavebiomes.core.Caves;
import com.blackgear.cavebiomes.core.registries.CaveBiomes;
import com.blackgear.cavebiomes.mixin.LayerAccessor;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
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
    public static void initializeCaveBiomes(Registry<Biome> biomeRegistry, long seed, int size) {
        caveLayer = CaveLayer.generateCaveLayers(biomeRegistry, seed, size);
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
                return sample(biomeRegistry, xIn, zIn);
            }
        }
        return surfaceBiomes;
    }

    /**
     * Don't use the vanilla layer method of func_242936_a.
     * It's bugged and checks the wrong registry first to resolve the biome id which can lead to crashes.
     *
     * @param dynamicBiomeRegistry - the registry vanilla should've grabbed the biome from first
     * @param x - position on x axis in world
     * @param z - position on z axis in world
     *
     * @return the dynamicregistry instance of the biome if done properly
     */
    public static Biome sample(Registry<Biome> dynamicBiomeRegistry, int x, int z) {
        int resultBiomeID = ((LayerAccessor)caveLayer).cavesapi_getSampler().getValue(x, z);
        Biome biome = dynamicBiomeRegistry.getByValue(resultBiomeID);
        if (biome == null) {
            if (SharedConstants.developmentMode) {
                throw Util.pauseDevMode(new IllegalStateException("Unknown biome id: " + resultBiomeID));
            } else {
                // Spawn ocean if we can't resolve the biome from the layers.
                RegistryKey<Biome> backupBiomeKey = BiomeRegistry.getKeyFromID(0);
                Caves.LOGGER.warn("Unknown biome id: ${}. Will spawn ${} instead.", resultBiomeID, backupBiomeKey.getLocation());
                return dynamicBiomeRegistry.getValueForKey(backupBiomeKey);
            }
        } else {
            return biome;
        }
    }

    /**
     * Injects a CaveBiome into the biomeLayer
     *
     * @see #addDefaultCaves()
     *
     * @param biome the biome for injection
     */
    public static void addCaveBiome(Biome biome) {
        if(biome == null || biome.getRegistryName() == null){
            throw new NullPointerException("CaveBiomeAPI's addCaveBiome method must take a registered biome. Null or unregistered biomes will be rejected.");
        }
        // Store the key as we will get the correct biome instance when the biome source is created.
        CaveLayer.caveBiomeKeys.add(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, biome.getRegistryName()));
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