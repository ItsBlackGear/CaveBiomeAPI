package com.blackgear.cavebiomes.common.world.feature;

import com.blackgear.cavebiomes.core.CaveBiome;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

//<>

public class GlobalConfiguredFeatures {
    public static final ConfiguredFeature<?, ?> SURFACE_ORE_DIRT = registerConfiguredFeatures("surface_ore_dirt", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.DIRT.getDefaultState(), 33)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(50, 0, 256))).square().count(10));
    public static final ConfiguredFeature<?, ?> SURFACE_ORE_GRAVEL = registerConfiguredFeatures("surface_ore_gravel", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.GRAVEL.getDefaultState(), 33)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(50, 0, 256))).square().count(8));
    public static final ConfiguredFeature<?, ?> SURFACE_ORE_GRANITE = registerConfiguredFeatures("surface_ore_granite", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.GRANITE.getDefaultState(), 33)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(50, 0, 80))).square().count(10));
    public static final ConfiguredFeature<?, ?> SURFACE_ORE_DIORITE = registerConfiguredFeatures("surface_ore_diorite", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.DIORITE.getDefaultState(), 33)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(50, 0, 80))).square().count(10));
    public static final ConfiguredFeature<?, ?> SURFACE_ORE_ANDESITE = registerConfiguredFeatures("surface_ore_andesite", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.ANDESITE.getDefaultState(), 33)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(50, 0, 80))).square().count(10));

    public static final ConfiguredFeature<?, ?> ORE_COAL = registerConfiguredFeatures("ore_coal", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.COAL_ORE.getDefaultState(), 17)).range(128).square().count(20));
    public static final ConfiguredFeature<?, ?> ORE_IRON = registerConfiguredFeatures("ore_iron", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.IRON_ORE.getDefaultState(), 9)).range(64).square().count(20));
    public static final ConfiguredFeature<?, ?> ORE_GOLD_EXTRA = registerConfiguredFeatures("ore_gold_extra", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.GOLD_ORE.getDefaultState(), 9)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(32, 32, 80))).square().count(20));
    public static final ConfiguredFeature<?, ?> ORE_GOLD = registerConfiguredFeatures("ore_gold", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.GOLD_ORE.getDefaultState(), 9)).range(32).square().count(2));
    public static final ConfiguredFeature<?, ?> ORE_REDSTONE = registerConfiguredFeatures("ore_redstone", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.REDSTONE_ORE.getDefaultState(), 8)).range(16).square().count(8));
    public static final ConfiguredFeature<?, ?> ORE_DIAMOND = registerConfiguredFeatures("ore_diamond", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.DIAMOND_ORE.getDefaultState(), 8)).range(16).square());
    public static final ConfiguredFeature<?, ?> ORE_LAPIS = registerConfiguredFeatures("ore_lapis", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.LAPIS_ORE.getDefaultState(), 7)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 16))).square());
    public static final ConfiguredFeature<?, ?> ORE_EMERALD = registerConfiguredFeatures("ore_emerald", Feature.EMERALD_ORE.withConfiguration(new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), Blocks.EMERALD_ORE.getDefaultState())).withPlacement(Placement.EMERALD_ORE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

    public static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> registerConfiguredFeatures(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        ResourceLocation ID = new ResourceLocation(CaveBiome.MOD_ID, key);
        if (WorldGenRegistries.CONFIGURED_FEATURE.keySet().contains(ID)) {
            throw new IllegalStateException("The Configured Feature " + key + "already exists in the registry");
        }
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, ID, configuredFeature);
        return configuredFeature;
    }
}