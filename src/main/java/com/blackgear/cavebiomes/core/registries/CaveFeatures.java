package com.blackgear.cavebiomes.core.registries;

import com.blackgear.cavebiomes.common.world.feature.CaveOreFeature;
import com.blackgear.cavebiomes.common.world.feature.CaveOreFeatureConfig;
import com.blackgear.cavebiomes.core.CaveBiome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

//<>

public class CaveFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, CaveBiome.MOD_ID);

    @Deprecated
    public static final RegistryObject<Feature<CaveOreFeatureConfig>> ORE_FEATURE = registerFeature("ore", () -> new CaveOreFeature(CaveOreFeatureConfig.CODEC));

    public static <C extends IFeatureConfig, F extends Feature<C>> RegistryObject<F> registerFeature(String key, Supplier<? extends F> carver) {
        return FEATURES.register(key, carver);
    }
}