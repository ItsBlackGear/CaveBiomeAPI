package com.blackgear.cavebiomes.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//<>

@Mixin(BiomeColors.class)
public abstract class BiomeColorsMixin {
    @Shadow @Final public static ColorResolver GRASS_COLOR_RESOLVER;
    @Shadow @Final public static ColorResolver FOLIAGE_COLOR_RESOLVER;
    @Shadow @Final public static ColorResolver WATER_COLOR_RESOLVER;

    @Shadow
    private static int getAverageColor(IBlockDisplayReader worldIn, BlockPos blockPosIn, ColorResolver colorResolverIn) {
        return worldIn.getBlockTint(blockPosIn, colorResolverIn);
    }

    @Inject(method = "getAverageGrassColor", at = @At("HEAD"), cancellable = true)
    private static void getGrassColor(IBlockDisplayReader worldIn, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(getAverageColor(worldIn, withY(pos, 64), GRASS_COLOR_RESOLVER));
    }

    @Inject(method = "getAverageFoliageColor", at = @At("HEAD"), cancellable = true)
    private static void getFoliageColor(IBlockDisplayReader worldIn, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(getAverageColor(worldIn, withY(pos, 64), FOLIAGE_COLOR_RESOLVER));
    }

    @Inject(method = "getAverageWaterColor", at = @At("HEAD"), cancellable = true)
    private static void getWaterColor(IBlockDisplayReader worldIn, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(getAverageColor(worldIn, withY(pos, 64), WATER_COLOR_RESOLVER));
    }

    @Unique
    private static BlockPos withY(BlockPos pos, int y) {
        return new BlockPos(pos.getX(), y, pos.getZ());
    }
}