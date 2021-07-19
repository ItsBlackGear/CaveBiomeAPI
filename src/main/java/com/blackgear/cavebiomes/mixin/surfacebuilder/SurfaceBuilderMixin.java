package com.blackgear.cavebiomes.mixin.surfacebuilder;

import com.blackgear.cavebiomes.core.utils.INoiseAccess;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

//<>

/**
 * Special thanks to the guys from InfernalExpansion! <3
 */
@Mixin(SurfaceBuilder.class)
public class SurfaceBuilderMixin implements INoiseAccess {
    @Override
    public BlockState modifyNoise(NoiseChunkGenerator generator, BlockPos pos, Random rand, BlockState state, IWorld worldIn, StructureManager structureManager, IChunk chunkIn) {
        return state;
    }
}