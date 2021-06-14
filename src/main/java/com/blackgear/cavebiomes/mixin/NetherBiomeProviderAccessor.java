package com.blackgear.cavebiomes.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.NetherBiomeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

//<>

/**
 * @author LudoCrypt
 */
@Mixin(NetherBiomeProvider.class)
public interface NetherBiomeProviderAccessor {
    @Invoker("<init>")
    public static NetherBiomeProvider createMultiNoiseBiomeProvider(long seed, List<Pair<Biome.Attributes, Supplier<Biome>>> biomePoints, Optional<Pair<Registry<Biome>, NetherBiomeProvider.Preset>> instance) {
        throw new UnsupportedOperationException();
    }
}