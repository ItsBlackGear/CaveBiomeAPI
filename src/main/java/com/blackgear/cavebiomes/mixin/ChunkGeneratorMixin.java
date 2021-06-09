package com.blackgear.cavebiomes.mixin;

import com.blackgear.cavebiomes.common.util.BiomeCoordinates;
import com.blackgear.cavebiomes.core.api.CaveLayer;
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
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//<>

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @Shadow @Final protected BiomeProvider biomeProvider;

//    /**
//     * @author BlackGear27 & CorgiTaco
//     */
//    @Overwrite
//    public void func_230351_a_(WorldGenRegion region, StructureManager manager) {
//        int mainChunkX = region.getMainChunkX();
//        int mainChunkZ = region.getMainChunkZ();
//        int x = mainChunkX * 16;
//        int z = mainChunkZ * 16;
//        BlockPos pos = new BlockPos(x, 0, z);
//        Biome biome = this.biomeProvider.getNoiseBiome(BiomeCoordinates.fromChunk(mainChunkX) + BiomeCoordinates.fromBlock(8), 64, BiomeCoordinates.fromChunk(mainChunkZ) + BiomeCoordinates.fromBlock(8));
//        Biome caveBiome = this.biomeProvider.getNoiseBiome(BiomeCoordinates.fromChunk(mainChunkX) + BiomeCoordinates.fromBlock(8), 5, BiomeCoordinates.fromChunk(mainChunkZ) + BiomeCoordinates.fromBlock(8));
//        SharedSeedRandom random = new SharedSeedRandom();
//        long seed = random.setDecorationSeed(region.getSeed(), x, z);
//
//        try {
//            biome.generateFeatures(manager, (ChunkGenerator)(Object)this, region, seed, random, pos);
//            caveBiome.generateFeatures(manager, (ChunkGenerator)(Object)this, region, seed, random, pos);
//        } catch (Exception exception) {
//            CrashReport crashReport = CrashReport.makeCrashReport(exception, "Biome decoration");
//            crashReport.makeCategory("Generation").addDetail("CenterX", mainChunkX).addDetail("CenterZ", mainChunkZ).addDetail("Seed", seed).addDetail("Biome", biome).addDetail("CaveBiome", caveBiome);
//            throw new ReportedException(crashReport);
//        }
//    }

    @Redirect(method = "func_230351_a_", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/provider/BiomeProvider;getNoiseBiome(III)Lnet/minecraft/world/biome/Biome;"))
    private Biome getSurfaceNoiseBiome(BiomeProvider biomeProvider, int x, int y, int z) {
        return biomeProvider.getNoiseBiome(x, 64, z);
    }

    @Inject(method = "func_230351_a_", at = @At("RETURN"), cancellable = true)
    private void getCaveNoiseBiome(WorldGenRegion region, StructureManager manager, CallbackInfo ci) {
        int mainChunkX = region.getMainChunkX();
        int mainChunkZ = region.getMainChunkZ();
        int x = mainChunkX * 16;
        int z = mainChunkZ * 16;
        BlockPos pos = new BlockPos(x, 0, z);
        Biome biome = this.biomeProvider.getNoiseBiome((x << 2) + 2, 10, (z << 2) + 2);
        if(!CaveLayer.caveBiomeSet.contains(biome)) return;

        SharedSeedRandom seedRandom = new SharedSeedRandom();
        long seed = seedRandom.setDecorationSeed(region.getSeed(), x, z);

        try {
            biome.generateFeatures(manager, (ChunkGenerator)(Object)this, region, seed, seedRandom, pos);
        } catch (Exception exception) {
            CrashReport report = CrashReport.makeCrashReport(exception, "Biome decoration");
            report.makeCategory("Generation").addDetail("CenterX", mainChunkX).addDetail("CenterZ", mainChunkZ).addDetail("Seed", seed).addDetail("Biome", biome);
            throw new ReportedException(report);
        }
    }
}