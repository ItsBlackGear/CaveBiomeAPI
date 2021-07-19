package com.blackgear.cavebiomes.core.api;

import com.blackgear.cavebiomes.core.CaveBiome;
import com.blackgear.cavebiomes.mixin.NetherBiomeProviderAccessor;
import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
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

    public static final NetherBiomeProvider.Preset CENTER_BIOME_PROVIDER = new NetherBiomeProvider.Preset(new ResourceLocation(CaveBiome.MOD_ID, "cave_biome_provider"), (preset, biomeRegistry, seed) -> {
        List<Pair<Biome.Attributes, Supplier<Biome>>> biomes = new ArrayList<>();
        CAVE_BIOMES.forEach((biomeKey, noisePoint) -> {
            Biome biome = biomeRegistry.getOrThrow(biomeKey);
            biomes.add(Pair.of(noisePoint, () -> biome));
        });
        return NetherBiomeProviderAccessor.createMultiNoiseBiomeProvider(seed, biomes, Optional.of(Pair.of(biomeRegistry, preset)));
    });
}