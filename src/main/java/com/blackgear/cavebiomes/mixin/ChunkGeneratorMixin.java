package com.blackgear.cavebiomes.mixin;

import com.blackgear.cavebiomes.common.util.BiomeCoordinates;
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
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;
import java.util.function.Predicate;

//<>

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @Shadow @Final protected BiomeProvider biomeProvider;

    @Redirect(method = "func_235958_g_()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/provider/BiomeProvider;findBiomePosition(IIIILjava/util/function/Predicate;Ljava/util/Random;)Lnet/minecraft/util/math/BlockPos;"))
    private BlockPos generateStrongholdPosition(BiomeProvider biomeProvider, int xIn, int yIn, int zIn, int radiusIn, Predicate<Biome> biomesIn, Random randIn) {
        return biomeProvider.findBiomePosition(xIn, 64, zIn, radiusIn, biomesIn, randIn);
    }

    @Redirect(method = "func_230350_a_(JLnet/minecraft/world/biome/BiomeManager;Lnet/minecraft/world/chunk/IChunk;Lnet/minecraft/world/gen/GenerationStage$Carving;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/provider/BiomeProvider;getNoiseBiome(III)Lnet/minecraft/world/biome/Biome;"))
    private Biome carve(BiomeProvider biomeProvider, int x, int y, int z) {
        return biomeProvider.getNoiseBiome(x, 64, z);
    }

    @Redirect(method = "func_235956_a_(Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/world/gen/feature/structure/Structure;Lnet/minecraft/util/math/BlockPos;IZ)Lnet/minecraft/util/math/BlockPos;", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos$Mutable;setPos(III)Lnet/minecraft/util/math/BlockPos$Mutable;"))
    private BlockPos.Mutable locateStructure(BlockPos.Mutable mutable, int xIn, int yIn, int zIn) {
        return mutable.setPos(xIn, 64, zIn);
    }

    /**
     * @author BlackGear27 & CorgiTaco
     */
    @Overwrite
    public void func_230351_a_(WorldGenRegion region, StructureManager manager) {
        int mainChunkX = region.getMainChunkX();
        int mainChunkZ = region.getMainChunkZ();
        int x = mainChunkX * 16;
        int z = mainChunkZ * 16;
        BlockPos pos = new BlockPos(x, 0, z);
        Biome biome = this.biomeProvider.getNoiseBiome(BiomeCoordinates.fromChunk(mainChunkX) + BiomeCoordinates.fromBlock(8), 64, BiomeCoordinates.fromChunk(mainChunkZ) + BiomeCoordinates.fromBlock(8));
        Biome caveBiome = this.biomeProvider.getNoiseBiome(BiomeCoordinates.fromChunk(mainChunkX) + BiomeCoordinates.fromBlock(8), 5, BiomeCoordinates.fromChunk(mainChunkZ) + BiomeCoordinates.fromBlock(8));
        SharedSeedRandom random = new SharedSeedRandom();
        long seed = random.setDecorationSeed(region.getSeed(), x, z);

        try {
            biome.generateFeatures(manager, (ChunkGenerator)(Object)this, region, seed, random, pos);
            caveBiome.generateFeatures(manager, (ChunkGenerator)(Object)this, region, seed, random, pos);
        } catch (Exception exception) {
            CrashReport crashReport = CrashReport.makeCrashReport(exception, "Biome decoration");
            crashReport.makeCategory("Generation").addDetail("CenterX", mainChunkX).addDetail("CenterZ", mainChunkZ).addDetail("Seed", seed).addDetail("Biome", biome).addDetail("CaveBiome", caveBiome);
            throw new ReportedException(crashReport);
        }
    }
}