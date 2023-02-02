package com.blackgear.cavebiomes.common.world.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

import java.util.List;

//<>

public class CaveOreFeatureConfig implements IFeatureConfig {
    public static final Codec<CaveOreFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.list(Target.CODEC).fieldOf("targets").forGetter((config) -> {
            return config.targets;
        }), Codec.intRange(0, 64).fieldOf("size").forGetter((config) -> {
            return config.size;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("discard_chance_on_air_exposure").forGetter((config) -> {
            return config.discardChanceOnAirExposure;
        })).apply(instance, CaveOreFeatureConfig::new);
    });
    public final List<Target> targets;
    public final int size;
    public final float discardChanceOnAirExposure;

    public CaveOreFeatureConfig(List<Target> targets, int size, float discardChanceOnAirExposure) {
        this.size = size;
        this.targets = targets;
        this.discardChanceOnAirExposure = discardChanceOnAirExposure;
    }

    public CaveOreFeatureConfig(List<Target> targets, int size) {
        this(targets, size, 0.0F);
    }

    public CaveOreFeatureConfig(RuleTest target, BlockState state, int size, float discardChanceOnAirExposure) {
        this(ImmutableList.of(new Target(target, state)), size, discardChanceOnAirExposure);
    }

    public CaveOreFeatureConfig(RuleTest target, BlockState state, int size) {
        this(ImmutableList.of(new Target(target, state)), size, 0.0F);
    }

    public static Target createTarget(RuleTest test, BlockState state) {
        return new Target(test, state);
    }

    public static class Target {
        public static final Codec<Target> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(RuleTest.CODEC.fieldOf("target").forGetter((target) -> {
                return target.target;
            }), BlockState.CODEC.fieldOf("state").forGetter((target) -> {
                return target.state;
            })).apply(instance, Target::new);
        });
        public final RuleTest target;
        public final BlockState state;

        Target(RuleTest target, BlockState state) {
            this.target = target;
            this.state = state;
        }
    }

    public static final class Rules {
        public static final RuleTest BASE_STONE_OVERWORLD = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest STONE_ORE_REPLACEABLES = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest BASE_STONE_NETHER = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }
}