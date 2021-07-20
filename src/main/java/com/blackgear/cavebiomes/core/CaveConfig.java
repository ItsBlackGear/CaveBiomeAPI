package com.blackgear.cavebiomes.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CaveConfig {
    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec common;

    public static final ForgeConfigSpec.BooleanValue generateNoiseCarvers;

    static {
        builder.push("worldgen");

            builder.push("experimental");
            generateNoiseCarvers    = builder.define("Generate Small Noise Caves", false);
            builder.pop();

        builder.pop();

        common = builder.build();
    }
}