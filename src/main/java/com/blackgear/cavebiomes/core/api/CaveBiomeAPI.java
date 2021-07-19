package com.blackgear.cavebiomes.core.api;

import com.blackgear.cavebiomes.core.CaveConfig;
import com.blackgear.cavebiomes.core.registries.CaveBiomes;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//<>

/**
 * Special Thanks to TelepathicGrunt and LudoCrypt!
 */
public class CaveBiomeAPI {
    private static NetherBiomeProvider caveBiomeProvider;

    /**
     * Initializes the cave generation into the biome provider.
     *
     * @implNote This initialization calls the biome provider seed and the cave biome size.
     *
     * @param seed the biome provider seed
     * @see com.blackgear.cavebiomes.mixin.OverworldBiomeProviderMixin#cba$initialize(long, boolean, boolean, Registry, CallbackInfo)
     */
    public static void initializeCaveBiomes(Registry<Biome> biomeRegistry, long seed) {
        caveBiomeProvider = CaveLayer.create(biomeRegistry, seed);
    }

    /**
     * Injects the CaveBiome generation into a biomeProvider
     *
     * @param surfaceBiomes the generated surface biomes
     * @param x the {@link net.minecraft.world.biome.provider.BiomeProvider#getNoiseBiome(int, int, int)} x value
     * @param y the {@link net.minecraft.world.biome.provider.BiomeProvider#getNoiseBiome(int, int, int)} y value
     * @param z the {@link net.minecraft.world.biome.provider.BiomeProvider#getNoiseBiome(int, int, int)} z value
     *
     * @return the CaveBiomes injected into the biomeProvider
     * @see com.blackgear.cavebiomes.mixin.OverworldBiomeProviderMixin#getNoiseBiome(int, int, int)
     */
    public static Biome injectCaveBiomes(Biome surfaceBiomes, int x, int y, int z) {
        if (CaveConfig.shouldGenerateCaveBiomes.get()) {
            if (y <= 12) {
                return caveBiomeProvider.getNoiseBiome(x, 0, z);
            }
        }
        return surfaceBiomes;
    }

    /**
     * Injects a CaveBiome into the biomeProvider
     *
     * @see #addDefaultCaves()
     *
     * @param biome the biome for injection
     * @param noise the mixed noise point used for generation
     */
    public static void addCaveBiome(RegistryKey<Biome> biome, Biome.Attributes noise) {
        if(biome == null || biome.getRegistryName() == null){
            throw new NullPointerException("CaveBiomeAPI's addCaveBiome method must take a registered biome. Null or unregistered biomes will be rejected.");
        }
        // Store the key as we will get the correct biome instance when the biome source is created.
        CaveLayer.addCaveBiome(biome, noise);
    }

    public static void addCaveBiome(Biome biome, Biome.Attributes noise) {
        if(biome == null || biome.getRegistryName() == null) {
            throw new NullPointerException("CaveBiomeAPI's addCaveBiome method must take a registered biome. Null or unregistered biomes will be rejected.");
        }

        addCaveBiome(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, biome.getRegistryName()), noise);
    }

    /**
     * Example of injection for Cave Biomes into the biomeProvider
     *
     * @see #addCaveBiome(Biome, Biome.Attributes)
     */
    public static void addDefaultCaves() {
        if (CaveConfig.HAS_DEFAULT_CAVE.get()) {
            CaveBiomeAPI.addCaveBiome(CaveBiomes.CAVE.get(), new Biome.Attributes(0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        }
    }
}