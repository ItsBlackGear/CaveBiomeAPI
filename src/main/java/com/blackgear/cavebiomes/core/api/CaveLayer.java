package com.blackgear.cavebiomes.core.api;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.ZoomLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;

//<>

public class CaveLayer {
    public static final List<Biome> biomes = new ArrayList<>();

    public static Layer generateCaveLayers(long seed, int biomeSize) {
        LongFunction<IExtendedNoiseRandom<LazyArea>> provider = salt -> new LazyAreaLayerContext(25, seed, salt);

        IAreaFactory<LazyArea> biomeFactory = new CaveMasterLayer(biomes).apply(provider.apply(200L));

        for (int size = 0; size < biomeSize; size++) {
            if ((size + 2) % 3 != 0) {
                biomeFactory = ZoomLayer.NORMAL.apply(provider.apply(2001L + size), biomeFactory);
            } else {
                biomeFactory = ZoomLayer.NORMAL.apply(provider.apply(2000L + (size * 31L)), biomeFactory);
            }
        }

        return new Layer(biomeFactory);
    }
}