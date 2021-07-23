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
    @Shadow @Final public static ColorResolver GRASS_COLOR;
    @Shadow @Final public static ColorResolver FOLIAGE_COLOR;
    @Shadow @Final public static ColorResolver WATER_COLOR;

    @Shadow
    private static int getBlockColor(IBlockDisplayReader worldIn, BlockPos blockPosIn, ColorResolver colorResolverIn) {
        return worldIn.getBlockColor(blockPosIn, colorResolverIn);
    }

    @Inject(method = "getGrassColor", at = @At("HEAD"), cancellable = true)
    private static void getGrassColor(IBlockDisplayReader worldIn, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(getBlockColor(worldIn, withY(pos, 64), GRASS_COLOR));
    }

    @Inject(method = "getFoliageColor", at = @At("HEAD"), cancellable = true)
    private static void getFoliageColor(IBlockDisplayReader worldIn, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(getBlockColor(worldIn, withY(pos, 64), FOLIAGE_COLOR));
    }

    @Inject(method = "getWaterColor", at = @At("HEAD"), cancellable = true)
    private static void getWaterColor(IBlockDisplayReader worldIn, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(getBlockColor(worldIn, withY(pos, 64), WATER_COLOR));
    }

    @Unique
    private static BlockPos withY(BlockPos pos, int y) {
        return new BlockPos(pos.getX(), y, pos.getZ());
    }
}