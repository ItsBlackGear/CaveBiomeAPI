package com.blackgear.cavebiomes.mixin.carver;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.gen.carver.WorldCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//<>

/**
 * if by any chance your block doesn't get carved, add them into the BASE_STONE_OVERWORLD tag
 */
@Mixin(WorldCarver.class)
public abstract class WorldCarverMixin {
    @Shadow protected abstract boolean isCarvable(BlockState state);

    @Inject(method = "canCarveBlock", at = @At("HEAD"), cancellable = true)
    protected void cba$canCarveBlock(BlockState state, BlockState aboveState, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.isCarvable(state) || (state.matchesBlock(Blocks.SAND) || state.matchesBlock(Blocks.GRAVEL)) && !aboveState.getFluidState().isTagged(FluidTags.WATER) || state.isIn(BlockTags.BASE_STONE_OVERWORLD));
    }
}