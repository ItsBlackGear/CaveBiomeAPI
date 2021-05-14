package com.blackgear.cavebiomes.core.api;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.List;

//<>

public class CaveBiomeCollector implements IAreaTransformer0 {
    private static final ForgeRegistry<Biome> BIOME = (ForgeRegistry<Biome>)ForgeRegistries.BIOMES;
    private final List<Biome> biomes;

    public CaveBiomeCollector(List<Biome> biomes) {
        this.biomes = biomes;
    }

    @Override
    public int apply(INoiseRandom random, int x, int z) {
        return BIOME.getID(this.biomes.get(random.random(this.biomes.size())));
    }
}