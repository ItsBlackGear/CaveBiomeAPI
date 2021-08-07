package com.blackgear.cavebiomes.mixin;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//<>

/**
 * @author TelepathicGrunt
 */

@Mixin(BiomeManager.class)
public class BiomeManagerMixin {
    @Redirect(method = "getBiomeAtPosition(III)Lnet/minecraft/world/biome/Biome;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/BiomeManager$IBiomeReader;getNoiseBiome(III)Lnet/minecraft/world/biome/Biome;"))
    private Biome cba$getBiomeAtPosition(BiomeManager.IBiomeReader iBiomeReader, int x, int y, int z) {
        return iBiomeReader.getNoiseBiome(x, 64, z);
    }
}