package com.blackgear.cavebiomes.mixin;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.spawner.WorldEntitySpawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

//<>

/**
 * @author TelepathicGrunt
 */
@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseChunkGeneratorMixin extends ChunkGenerator {
    @Shadow @Final protected Supplier<DimensionSettings> field_236080_h_;

    public NoiseChunkGeneratorMixin(BiomeProvider p_i231888_1_, DimensionStructuresSettings p_i231888_2_) {
        super(p_i231888_1_, p_i231888_2_);
    }

//    @ModifyArg(method = "Lnet/minecraft/world/gen/NoiseChunkGenerator;func_230354_a_(Lnet/minecraft/world/gen/WorldGenRegion;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/WorldGenRegion;getBiome(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/world/biome/Biome;"), index = 0)
//    public BlockPos cba$populateSurfaceEntities(BlockPos pos) {
//        return new BlockPos(pos.getX(), 64, pos.getZ());
//    }

    @Inject(method = "func_230354_a_(Lnet/minecraft/world/gen/WorldGenRegion;)V", at = @At("HEAD"))
    public void cba$populateSurfaceEntities(WorldGenRegion region, CallbackInfo ci) {
        if (!this.field_236080_h_.get().func_236120_h_()) {
            int x = region.getMainChunkX();
            int z = region.getMainChunkZ();
            Biome biome = region.getBiome(new BlockPos(x, 64, z));
            SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
            sharedseedrandom.setDecorationSeed(region.getSeed(), x << 4, z << 4);
            WorldEntitySpawner.performWorldGenSpawning(region, biome, x, z, sharedseedrandom);
        }
    }
}