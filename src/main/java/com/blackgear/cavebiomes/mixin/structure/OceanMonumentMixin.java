package com.blackgear.cavebiomes.mixin.structure;

import com.blackgear.cavebiomes.core.api.CaveLayer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.feature.structure.OceanMonumentStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

//<>

/**
 * @author TelepathicGrunt
 */
@Mixin(OceanMonumentStructure.class)
public class OceanMonumentMixin {
    @Redirect(method = "isFeatureChunk(Lnet/minecraft/world/gen/ChunkGenerator;Lnet/minecraft/world/biome/provider/BiomeProvider;JLnet/minecraft/util/SharedSeedRandom;IILnet/minecraft/world/biome/Biome;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/gen/feature/NoFeatureConfig;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/provider/BiomeProvider;getBiomesWithin(IIII)Ljava/util/Set;"))
//    @Redirect(method = "func_230363_a_(Lnet/minecraft/world/gen/ChunkGenerator;Lnet/minecraft/world/biome/provider/BiomeProvider;JLnet/minecraft/util/SharedSeedRandom;IILnet/minecraft/world/biome/Biome;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/world/gen/feature/NoFeatureConfig;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/provider/BiomeProvider;getBiomes(IIII)Ljava/util/Set;"))
    private Set<Biome> cba$getSurfaceBiomes(BiomeProvider biomeProvider, int xIn, int yIn, int zIn, int radius) {
        Set<Biome> biomeSet = biomeProvider.getBiomesWithin(xIn, yIn, zIn, radius);
        biomeSet.removeIf(CaveLayer.CAVE_BIOME_LIST::contains);
        return biomeSet;
    }
}