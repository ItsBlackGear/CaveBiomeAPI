package com.blackgear.cavebiomes.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = Caves.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CaveConfig {
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_CONFIG;

    public static final ForgeConfigSpec.IntValue CAVE_BIOME_SIZE;
    public static final ForgeConfigSpec.BooleanValue HAS_CAVE_BIOMES;
    public static final ForgeConfigSpec.BooleanValue HAS_NOISE_CARVERS;
    public static final ForgeConfigSpec.BooleanValue HAS_DEFAULT_CAVE;

    static {
        COMMON_BUILDER.push("World Generation Settings");
        CAVE_BIOME_SIZE = COMMON_BUILDER.comment("determines the size of cave biomes globally.", "Default: 4, Amplified: 6").defineInRange("caveBiomeSize", 4, 1, 10);
        HAS_CAVE_BIOMES = COMMON_BUILDER.comment("determines if cave biomes generate naturally.").define("hasCaveBiomes", true);
        HAS_NOISE_CARVERS = COMMON_BUILDER.comment("determines if noise carvers should generate").comment("noise carvers are a smaller version of 1.17 noise caves").define("hasNoiseCarvers", true);
        HAS_DEFAULT_CAVE = COMMON_BUILDER.comment("determines if default cave biome should generate in the world", "Note: to disable ALL the biomes use hasCaveBiomes instead, don't disable all biomes manually or you will break something").define("hasDefaultCaveBiome", true);
        COMMON_CONFIG   = COMMON_BUILDER.build();
    }
}