package com.blackgear.cavebiomes.core.api;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

import java.util.List;

//<>

public class CaveMasterLayer implements IAreaTransformer0 {
    public final Registry<Biome> dynamicBiomeRegistry;
    private final List<Biome> biomes;

    public CaveMasterLayer(Registry<Biome> biomeRegistry, List<Biome> biomes) {
        this.dynamicBiomeRegistry = biomeRegistry;
        this.biomes = biomes;
    }

    @Override
    public int apply(INoiseRandom random, int x, int z) {
        return this.dynamicBiomeRegistry.getId(this.biomes.get(random.random(this.biomes.size())));
    }
}