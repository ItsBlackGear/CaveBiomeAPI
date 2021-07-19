package com.blackgear.cavebiomes.core.utils;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.List;
import java.util.function.Supplier;

//<>

/**
 * @author TelepathicGrunt
 */
public class FeatureGenerationHelper {
    /**
     * Will not spawn any structure and instead, only features.
     */
    public static void generateOnlyFeatures(Biome biome, ChunkGenerator chunkGenerator, WorldGenRegion worldGenRegion, long seed, SharedSeedRandom rand, BlockPos pos) {
        List<List<Supplier<ConfiguredFeature<?, ?>>>> list = biome.getGenerationSettings().getFeatures();
        for (int generationStageIndex = 0; generationStageIndex < GenerationStage.Decoration.values().length; ++generationStageIndex) {
            int featureIndex = 1001; // offset index by 1001 so decorators for features do not exactly line up with features on surface biomes.
            if (list.size() > generationStageIndex) {
                for (Supplier<ConfiguredFeature<?, ?>> supplier : list.get(generationStageIndex)) {
                    ConfiguredFeature<?, ?> configuredFeature = supplier.get();
                    rand.setFeatureSeed(seed, featureIndex, generationStageIndex);

                    try {
                        configuredFeature.generate(worldGenRegion, chunkGenerator, rand, pos);
                    }
                    catch (Exception exception) {
                        CrashReport crashReport = CrashReport.makeCrashReport(exception, "Feature placement");
                        crashReport.makeCategory("Feature")
                                .addDetail("Id", Registry.FEATURE.getKey(configuredFeature.feature))
                                .addDetail("Config", configuredFeature.config)
                                .addDetail("Description", configuredFeature.feature.toString());
                        throw new ReportedException(crashReport);
                    }

                    ++featureIndex;
                }
            }
        }
    }
}
