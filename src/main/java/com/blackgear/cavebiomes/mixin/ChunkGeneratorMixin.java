package com.blackgear.cavebiomes.mixin;

import com.blackgear.cavebiomes.core.api.CaveLayer;
import com.blackgear.cavebiomes.core.utils.FeatureGenerationHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//<>

/**
 * Special thanks to TelepathicGrunt that helped to fix not one but almost every issue with this code on it's experimental stage.
 */
@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @Shadow @Final protected BiomeProvider biomeProvider;

    @Redirect(method = "func_230351_a_(Lnet/minecraft/world/gen/WorldGenRegion;Lnet/minecraft/world/gen/feature/structure/StructureManager;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/provider/BiomeProvider;getNoiseBiome(III)Lnet/minecraft/world/biome/Biome;"))
    private Biome cba$generateSurfaceFeatures(BiomeProvider provider, int x, int y, int z) {
        return provider.getNoiseBiome(x, 64, z);
    }

    @Inject(method = "func_230351_a_(Lnet/minecraft/world/gen/WorldGenRegion;Lnet/minecraft/world/gen/feature/structure/StructureManager;)V", at = @At("RETURN"), cancellable = true)
    private void cba$generateUndergroundFeatures(WorldGenRegion region, StructureManager manager, CallbackInfo ci) {
        int mainChunkX = region.getMainChunkX();
        int mainChunkZ = region.getMainChunkZ();
        int x = mainChunkX * 16;
        int z = mainChunkZ * 16;
        BlockPos pos = new BlockPos(x, 0, z);
        Biome biome = this.biomeProvider.getNoiseBiome((mainChunkX << 2) + 2, 10, (mainChunkZ << 2) + 2);
        if(!CaveLayer.CAVE_BIOME_LIST.contains(biome)) return;

        SharedSeedRandom seedRandom = new SharedSeedRandom();
        long seed = seedRandom.setDecorationSeed(region.getSeed(), x, z);

        try {
            FeatureGenerationHelper.generateOnlyFeatures(biome, (ChunkGenerator)(Object)this, region, seed, seedRandom, pos);
        } catch (Exception exception) {
            CrashReport report = CrashReport.makeCrashReport(exception, "Biome decoration");
            report.makeCategory("Generation").addDetail("CenterX", mainChunkX).addDetail("CenterZ", mainChunkZ).addDetail("Seed", seed).addDetail("Biome", biome);
            throw new ReportedException(report);
        }
    }

    /**
     * @author TelepathicGrunt
     */
    @Redirect(method = "func_242707_a(Lnet/minecraft/util/registry/DynamicRegistries;Lnet/minecraft/world/gen/feature/structure/StructureManager;Lnet/minecraft/world/chunk/IChunk;Lnet/minecraft/world/gen/feature/template/TemplateManager;J)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/provider/BiomeProvider;getNoiseBiome(III)Lnet/minecraft/world/biome/Biome;"))
    private Biome cba$setStructureStarts(BiomeProvider biomeProvider, int x, int y, int z) {
        return biomeProvider.getNoiseBiome(x, 64, z);
    }

    @Redirect(method = "func_230350_a_(JLnet/minecraft/world/biome/BiomeManager;Lnet/minecraft/world/chunk/IChunk;Lnet/minecraft/world/gen/GenerationStage$Carving;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/provider/BiomeProvider;getNoiseBiome(III)Lnet/minecraft/world/biome/Biome;"))
    private Biome cba$generateSurfaceCarvers(BiomeProvider provider, int x, int y, int z) {
        return provider.getNoiseBiome(x, 64, z);
    }
}