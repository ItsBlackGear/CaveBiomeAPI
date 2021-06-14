package com.blackgear.cavebiomes.mixin;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//<>

/**
 * @author TelepathicGrunt
 */
@Mixin(BiomeManager.class)
public class BiomeManagerMixin {
    @Redirect(method = "getBiomeAtPosition(III)Lnet/minecraft/world/biome/Biome;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/BiomeManager$IBiomeReader;getNoiseBiome(III)Lnet/minecraft/world/biome/Biome;"))
    private Biome cba$generateUndergroundFeatures(BiomeManager.IBiomeReader iBiomeReader, int x, int y, int z) {
        return iBiomeReader.getNoiseBiome(x, 64, z);
    }
}