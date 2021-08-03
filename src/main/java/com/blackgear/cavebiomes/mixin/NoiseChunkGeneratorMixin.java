package com.blackgear.cavebiomes.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

//

/**
 * @author TelepathicGrunt
 */
@Mixin(NoiseChunkGenerator.class)
public class NoiseChunkGeneratorMixin {
    @ModifyArg(method = "func_230354_a_(Lnet/minecraft/world/gen/WorldGenRegion;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/WorldGenRegion;getBiome(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/world/biome/Biome;"))
    public BlockPos cba$populateSurfaceEntities(BlockPos pos) {
        return new BlockPos(pos.getX(), 64, pos.getZ());
    }
}