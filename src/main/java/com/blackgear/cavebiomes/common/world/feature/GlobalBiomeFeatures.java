package com.blackgear.cavebiomes.common.world.feature;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;

//<>

public class GlobalBiomeFeatures {
    public static void withCommonUndergroundBlocks(BiomeGenerationSettings.Builder builder) {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DIRT);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_GRAVEL);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_GRANITE);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DIORITE);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_ANDESITE);
    }
}