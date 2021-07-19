package com.blackgear.cavebiomes.common.world.surfacebuilders;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

//<>

public class LayeredCaveSurfaceBuilder extends CaveSurfaceBuilder {
    private final BlockState defaultBlock;
    private final BlockState baseBlock;
    private final int layerHeight;

    public LayeredCaveSurfaceBuilder(BlockState defaultBlock, BlockState baseBlock, int layerHeight) {
        super(SurfaceBuilderConfig.CODEC);
        this.defaultBlock = defaultBlock;
        this.baseBlock = baseBlock;
        this.layerHeight = layerHeight;
    }

    @Override
    protected BlockState defaultBlock() {
        return this.defaultBlock;
    }

    @Override
    protected BlockState baseBlock() {
        return this.baseBlock;
    }

    @Override
    protected int layerHeight() {
        return this.layerHeight;
    }
}