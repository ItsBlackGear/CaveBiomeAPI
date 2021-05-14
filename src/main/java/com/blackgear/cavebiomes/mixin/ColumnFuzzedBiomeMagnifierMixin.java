package com.blackgear.cavebiomes.mixin;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.ColumnFuzzedBiomeMagnifier;
import net.minecraft.world.biome.FuzzedBiomeMagnifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ColumnFuzzedBiomeMagnifier.class)
public class ColumnFuzzedBiomeMagnifierMixin {
    /**
     * @author CorgiTaco
     */
    @Overwrite
    public Biome getBiome(long seed, int x, int y, int z, BiomeManager.IBiomeReader biomeReader) {
        return FuzzedBiomeMagnifier.INSTANCE.getBiome(seed, x, y, z, biomeReader);
    }
}