package com.blackgear.cavebiomes.mixin.feature;

import com.blackgear.cavebiomes.common.world.feature.GlobalConfiguredFeatures;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//<>

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {
    @Inject(method = "withCommonOverworldBlocks", at = @At("HEAD"), cancellable = true)
    private static void cba$withCommonOverworldBlocks(BiomeGenerationSettings.Builder builder, CallbackInfo ci) {
        ci.cancel();
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GlobalConfiguredFeatures.SURFACE_ORE_DIRT);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GlobalConfiguredFeatures.SURFACE_ORE_GRAVEL);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GlobalConfiguredFeatures.SURFACE_ORE_GRANITE);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GlobalConfiguredFeatures.SURFACE_ORE_DIORITE);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GlobalConfiguredFeatures.SURFACE_ORE_ANDESITE);
    }
}