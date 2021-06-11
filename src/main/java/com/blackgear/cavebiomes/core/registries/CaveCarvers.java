package com.blackgear.cavebiomes.core.registries;

import com.blackgear.cavebiomes.common.world.feature.carver.NoiseCarver;
import com.blackgear.cavebiomes.core.Caves;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

//<>

public class CaveCarvers {
    public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, Caves.MODID);

    public static final RegistryObject<WorldCarver<ProbabilityConfig>> NOISE_CARVER = registerCarver("noise_carver", () -> new NoiseCarver(ProbabilityConfig.CODEC));

    public static <C extends ICarverConfig, W extends WorldCarver<C>> RegistryObject<W> registerCarver(String key, Supplier<? extends W> carver) {
        return CARVERS.register(key, carver);
    }
}