package com.blackgear.cavebiomes.core.api;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class GlobalBiomeManager {
    private final BiomeLoadingEvent event;

    public GlobalBiomeManager(BiomeLoadingEvent event) {
        this.event = event;
    }

    public ResourceLocation name() {
        return this.event.getName();
    }

    public Biome.Category category() {
        return this.event.getCategory();
    }

    public BiomeGenerationSettingsBuilder generation() {
        return this.event.getGeneration();
    }

    public MobSpawnInfoBuilder spawns() {
        return this.event.getSpawns();
    }

    public boolean matches(Biome biome) {
        Biome biomeToMatch = ForgeRegistries.BIOMES.getValue(this.name());
        return biome.equals(biomeToMatch);
    }

    public boolean matches(RegistryKey<Biome> biomeKey) {
        RegistryKey<Biome> key = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, this.name());
        return biomeKey.equals(key);
    }

    //the beach biome is the only biome in the overworld under the NONE category.
    public boolean canGenerate() {
        return (this.category() != Biome.Category.NETHER || this.category() != Biome.Category.THEEND || this.matches(Biomes.BEACH)) && this.name() != null;
    }
}