package com.blackgear.cavebiomes.core.api;

import com.blackgear.cavebiomes.core.CavesAPI;
import com.blackgear.cavebiomes.mixin.NetherBiomeProviderAccessor;
import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.NetherBiomeProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

//<>

/**
 * @author LudoCrypt
 */
public class CaveLayer {
    public static final Map<RegistryKey<Biome>, Biome.Attributes> CAVE_BIOMES = new HashMap<>();
    public static final List<Biome> CAVE_BIOME_LIST = new ArrayList<>();

    public static NetherBiomeProvider create(Registry<Biome> biomes, long seed) {
        CAVE_BIOME_LIST.addAll(CAVE_BIOMES.keySet().stream().map(biomes::getValueForKey).collect(Collectors.toList()));
        return CENTER_BIOME_PROVIDER.build(biomes, seed);
    }

    public static void addCaveBiome(RegistryKey<Biome> biome, Biome.Attributes noise) {
        Preconditions.checkNotNull(biome, "biome is null");
        Preconditions.checkNotNull(noise, "noise is null");
        CAVE_BIOMES.put(biome, noise);
    }

    public static final NetherBiomeProvider.Preset CENTER_BIOME_PROVIDER = new NetherBiomeProvider.Preset(new ResourceLocation(CavesAPI.MOD_ID, "cave_biome_provider"), (preset, biomeRegistry, seed) -> {
        List<Pair<Biome.Attributes, Supplier<Biome>>> biomes = new ArrayList<>();
        CAVE_BIOMES.forEach((biomeKey, noisePoint) -> {
            Biome biome = biomeRegistry.getOrThrow(biomeKey);
            biomes.add(Pair.of(noisePoint, () -> biome));
        });
        NetherBiomeProvider.Noise temperatureNoise = createNoiseParameters(-9, 1.0D, 0.0D, 3.0D, 3.0D, 3.0D, 3.0D);
        NetherBiomeProvider.Noise humidityNoise = createNoiseParameters(-7, 1.0D, 2.0D, 4.0D, 4.0D);
        NetherBiomeProvider.Noise altitudeNoise = createNoiseParameters(-9, 1.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0D);
        NetherBiomeProvider.Noise weirdnessNoise = createNoiseParameters(-8, 1.2D, 0.6D, 0.0D, 0.0D, 1.0D, 0.0D);

        return NetherBiomeProviderAccessor.createNetherBiomeProvider(seed, biomes, temperatureNoise, humidityNoise, altitudeNoise, weirdnessNoise, Optional.empty());
    });

    private static NetherBiomeProvider.Noise createNoiseParameters(int firstOctave, double... amplitudes) {
        return new NetherBiomeProvider.Noise(firstOctave, new DoubleArrayList(amplitudes));
    }
}