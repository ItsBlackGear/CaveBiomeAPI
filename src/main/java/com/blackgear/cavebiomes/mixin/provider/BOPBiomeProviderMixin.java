package com.blackgear.cavebiomes.mixin.provider;

import biomesoplenty.common.world.BOPBiomeProvider;
import com.blackgear.cavebiomes.core.CaveConfig;
import com.blackgear.cavebiomes.core.api.CaveBiomeAPI;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.Layer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//<>

@Pseudo @Mixin(BOPBiomeProvider.class)
public class BOPBiomeProviderMixin {
    @Shadow @Final private Layer noiseBiomeLayer;
    @Shadow @Final private Registry<Biome> biomes;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void initialize(long seed, Registry<Biome> biomes, CallbackInfo info) {
        CaveBiomeAPI.initializeCaveBiomes(seed, CaveConfig.caveBiomeSize.get());
    }

    /**
     * @author BlackGear27
     */
    @Overwrite
    public Biome getNoiseBiome(int xIn, int yIn, int zIn) {
        Biome surfaceBiome = this.noiseBiomeLayer.func_242936_a(this.biomes, xIn, zIn);
        return CaveBiomeAPI.injectCaveBiomes(surfaceBiome, this.biomes, xIn, yIn, zIn);
    }
}