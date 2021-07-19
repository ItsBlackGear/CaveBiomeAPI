package com.blackgear.cavebiomes.common.world.surfacebuilders;

import com.blackgear.cavebiomes.core.utils.INoiseAccess;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

//<>

public abstract class CaveSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> implements INoiseAccess {
    public CaveSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
    }

    @Override
    public BlockState modifyNoise(NoiseChunkGenerator generator, BlockPos pos, Random rand, BlockState state, IWorld worldIn, StructureManager structureManager, IChunk chunkIn) {
        if (state.getBlock().isIn(BlockTags.BASE_STONE_OVERWORLD)) {
            if (pos.getY() < layerHeight()) {
                return this.baseBlock();
            } else {
                return this.defaultBlock();
            }
        }
        return state;
    }

    protected abstract BlockState defaultBlock();

    protected abstract BlockState baseBlock();

    protected abstract int layerHeight();
}