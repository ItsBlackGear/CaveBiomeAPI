package com.blackgear.cavebiomes.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = Caves.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CaveConfig {
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.IntValue caveBiomeSize;
    public static ForgeConfigSpec.BooleanValue hasCaveBiomes;
    public static ForgeConfigSpec.BooleanValue hasNoiseCarvers;
    public static ForgeConfigSpec.BooleanValue hasDefaultCave;

    static {
        COMMON_BUILDER.push("World Generation Settings");
        caveBiomeSize   = COMMON_BUILDER.comment("determines the size of cave biomes globally.").comment("Default: 4, Amplified: 6").defineInRange("caveBiomeSize", 4, 1, 10);
        hasCaveBiomes   = COMMON_BUILDER.comment("determines if cave biomes generate naturally.").define("hasCaveBiomes", true);
        hasNoiseCarvers = COMMON_BUILDER.comment("determines if noise carvers should generate").comment("noise carvers are a smaller version of 1.17 noise caves").define("hasNoiseCarvers", true);
        hasDefaultCave  = COMMON_BUILDER.comment("determines if default cave biome should generate in the world").comment("important note: to disable ALL the biomes use hasCaveBiomes instead, don't disable all biomes manually or you will break something").define("hasDefaultCaveBiome", true);
        COMMON_CONFIG   = COMMON_BUILDER.build();
    }
}