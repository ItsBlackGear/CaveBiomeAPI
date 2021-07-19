package com.blackgear.cavebiomes.core.utils;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

//<>

/**
 * Special thanks to the guys from InfernalExpansion! <3
 */
public interface INoiseAccess {
    BlockState modifyNoise(NoiseChunkGenerator generator, BlockPos pos, Random rand, BlockState state, IWorld worldIn, StructureManager structureManager, IChunk chunkIn);
}