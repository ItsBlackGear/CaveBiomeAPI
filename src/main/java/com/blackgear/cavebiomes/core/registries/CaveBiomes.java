package com.blackgear.cavebiomes.core.registries;

import com.blackgear.cavebiomes.core.CavesAPI;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

//<>

public class CaveBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, CavesAPI.MOD_ID);

    public static final RegistryObject<Biome> CAVE = registerBiome("caves", CaveBiomes::makeDefaultCaves);

    private static <B extends Biome> RegistryObject<B> registerBiome(String key, Supplier<? extends B> biome) {
        return BIOMES.register(key, biome);
    }

    public static Biome makeDefaultCaves() {
        MobSpawnInfo.Builder spawnSettings = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.commonSpawns(spawnSettings);
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder().surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
        DefaultBiomeFeatures.addDefaultCarvers(generationSettings);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.NONE).depth(0.125F).scale(0.05F).temperature(0.8F).downfall(0.4F).specialEffects(new BiomeAmbience.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier()).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    private static int getSkyColorWithTemperatureModifier() {
        float modifier = 0.8F / 3.0F;
        modifier = MathHelper.clamp(modifier, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - modifier * 0.05F, 0.5F + modifier * 0.1F, 1.0F);
    }
}