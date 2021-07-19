package com.blackgear.cavebiomes.core.registries;

import com.blackgear.cavebiomes.common.world.surfacebuilders.DefaultCaveSurfaceBuilder;
import com.blackgear.cavebiomes.core.CaveBiome;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

//<>

public class CaveSurfaceBuilders {
    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDER = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, CaveBiome.MOD_ID);

    /**
     * this SurfaceBuilder will generate a STONE surface only
     */
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> DEFAULT_CAVE = registerSurfaceBuilder("default_cave", () -> new DefaultCaveSurfaceBuilder(Blocks.GRANITE.getDefaultState()));

    /**
     * this SurfaceBuilder should generate an ANDESITE cave surface, followed by a GRANITE layer that will start at y16
     * public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> LAYERED_CAVE = registerSurfaceBuilder("layered_cave", () -> new LayeredCaveSurfaceBuilder(Blocks.ANDESITE.getDefaultState(), Blocks.GRANITE.getDefaultState(), 16));
     */

    public static <C extends ISurfaceBuilderConfig, S extends SurfaceBuilder<C>> RegistryObject<S> registerSurfaceBuilder(String key, Supplier<? extends S> surfaceBuilder) {
        return SURFACE_BUILDER.register(key, surfaceBuilder);
    }
}