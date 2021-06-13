package com.blackgear.cavebiomes.core.registries;

import com.blackgear.cavebiomes.core.Caves;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

//<>

public class CaveBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Caves.MODID);

    public static final RegistryObject<Biome> CAVE = registerBiome("caves", CaveBiomes::makeDefaultCaves);

    private static <B extends Biome> RegistryObject<B> registerBiome(String key, Supplier<? extends B> biome) {
        return BIOMES.register(key, biome);
    }

    public static Biome makeDefaultCaves() {
        MobSpawnInfo.Builder spawnSettings = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withBatsAndHostiles(spawnSettings);
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.field_244178_j);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(generationSettings);
        generationSettings.withStructure(StructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.withCavesAndCanyons(generationSettings);
        DefaultBiomeFeatures.withLavaAndWaterLakes(generationSettings);
        DefaultBiomeFeatures.withMonsterRoom(generationSettings);
        DefaultBiomeFeatures.withCommonOverworldBlocks(generationSettings);
        DefaultBiomeFeatures.withOverworldOres(generationSettings);
        DefaultBiomeFeatures.withNormalMushroomGeneration(generationSettings);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(generationSettings);
        DefaultBiomeFeatures.withLavaAndWaterSprings(generationSettings);
        DefaultBiomeFeatures.withFrozenTopLayer(generationSettings);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.125F).scale(0.05F).temperature(0.8F).downfall(0.4F).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(getSkyColorWithTemperatureModifier()).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(spawnSettings.copy()).withGenerationSettings(generationSettings.build()).build();
    }

    private static int getSkyColorWithTemperatureModifier() {
        float modifier = 0.8F / 3.0F;
        modifier = MathHelper.clamp(modifier, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - modifier * 0.05F, 0.5F + modifier * 0.1F, 1.0F);
    }
}