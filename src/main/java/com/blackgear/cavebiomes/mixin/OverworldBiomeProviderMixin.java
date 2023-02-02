package com.blackgear.cavebiomes.mixin;

import com.blackgear.cavebiomes.core.api.CaveBiomeAPI;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.layer.Layer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//<>

@Mixin(OverworldBiomeProvider.class)
public class OverworldBiomeProviderMixin {
    @Shadow @Final private Layer noiseBiomeLayer;
    @Shadow @Final private Registry<Biome> biomes;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void cba$initialize(long seed, boolean legacyBiomeInitLayer, boolean largeBiomes, Registry<Biome> biomeRegistry, CallbackInfo ci) {
        CaveBiomeAPI.initializeCaveBiomes(biomeRegistry, seed);
    }

    /**
     * @author BlackGear27
     * @reason collecting the surface biomes and injecting them along the underground biomes
     */
    @Overwrite
    public Biome getNoiseBiome(int xIn, int yIn, int zIn) {
        Biome surfaceBiome = this.noiseBiomeLayer.get(this.biomes, xIn, zIn);
        return CaveBiomeAPI.injectCaveBiomes(surfaceBiome, xIn, yIn, zIn);
    }
}