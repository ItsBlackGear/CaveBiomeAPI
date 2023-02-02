package com.blackgear.cavebiomes.common.world.feature;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.function.Function;
import java.util.function.Predicate;

//<>

public class FeatureUtils {
    public static boolean testAdjacentStates(Function<BlockPos, BlockState> posToState, BlockPos pos, Predicate<BlockState> predicate) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            mutable.setWithOffset(pos, direction);
            if (predicate.test(posToState.apply(mutable))) {
                return true;
            }
        }

        return false;
    }

    public static boolean isExposedToAir(Function<BlockPos, BlockState> posToState, BlockPos pos) {
        return testAdjacentStates(posToState, pos, AbstractBlock.AbstractBlockState::isAir);
    }
}