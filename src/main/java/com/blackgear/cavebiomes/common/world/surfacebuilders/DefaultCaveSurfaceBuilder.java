package com.blackgear.cavebiomes.common.world.surfacebuilders;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

//<>

public class DefaultCaveSurfaceBuilder extends CaveSurfaceBuilder {
    private final BlockState defaultBlock;

    public DefaultCaveSurfaceBuilder(BlockState defaultBlock) {
        super(SurfaceBuilderConfig.CODEC);
        this.defaultBlock = defaultBlock;
    }

    @Override
    protected BlockState defaultBlock() {
        return this.defaultBlock;
    }

    @Override
    protected BlockState baseBlock() {
        return this.defaultBlock;
    }

    @Override
    protected int layerHeight() {
        return 0;
    }
}