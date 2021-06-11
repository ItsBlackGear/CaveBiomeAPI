package com.blackgear.cavebiomes.compat;

import net.minecraft.block.Block;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ObjectHolder;

//<>

public class DarkerDepths {
    @ObjectHolder("darkerdepths:aridrock")
    public static final Block ARIDROCK = null;
    @ObjectHolder("darkerdepths:limestone")
    public static final Block LIMESTONE = null;

    public static boolean isInstalled() {
        return ModList.get() != null && ModList.get().getModContainerById("darkerdepths").isPresent();
    }
}