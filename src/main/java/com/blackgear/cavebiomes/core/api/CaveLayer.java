package com.blackgear.cavebiomes.core.api;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.ZoomLayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

//<>

public class CaveLayer {
    public static final List<RegistryKey<Biome>> caveBiomeKeys = new ArrayList<>();
    public static final List<Biome> caveBiomes = new ArrayList<>(); // For biome spawning
    public static final Set<Biome> caveBiomeSet = new HashSet<>(); // For quick checking if a biome is a cave biome

    public static Layer generateCaveLayers(Registry<Biome> biomeRegistry, long seed, int biomeSize) {
        // Clear set and list of cave biomes so we can get the correct biome instance from the world's dynamic registry.
        caveBiomes.clear();
        caveBiomeSet.clear();
        caveBiomes.addAll(caveBiomeKeys.stream().map(biomeRegistry::getValueForKey).collect(Collectors.toList()));
        caveBiomeSet.addAll(caveBiomes);

        LongFunction<IExtendedNoiseRandom<LazyArea>> provider = salt -> new LazyAreaLayerContext(25, seed, salt);

        IAreaFactory<LazyArea> biomeFactory = new CaveMasterLayer(biomeRegistry, caveBiomes).apply(provider.apply(200L));

        for (int size = 0; size < biomeSize; size++) {
            if ((size + 2) % 3 != 0) {
                biomeFactory = ZoomLayer.NORMAL.apply(provider.apply(2001L + size), biomeFactory);
            } else {
                biomeFactory = ZoomLayer.NORMAL.apply(provider.apply(2000L + (size * 31L)), biomeFactory);
            }
        }

        return new Layer(biomeFactory);
    }
}