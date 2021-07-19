package com.blackgear.cavebiomes.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = CaveBiome.MOD_ID)
public class CaveBiomeConfig {
    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec common;

    public static final ForgeConfigSpec.BooleanValue generateNoiseCarvers;
    public static final ForgeConfigSpec.BooleanValue generateDefaultCave;

    static {
        builder.push("worldgen");
            builder.push("biomes");
            generateDefaultCave     = builder.define("Generate Default Cave Biome in the Underground", true);
            builder.pop();

            builder.push("experimental");
            generateNoiseCarvers    = builder.define("Generate Small Noise Caves", false);
            builder.pop();
        builder.pop();

        common = builder.build();
    }
}