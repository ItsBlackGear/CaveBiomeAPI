package com.blackgear.cavebiomes.common.world;

import com.blackgear.cavebiomes.core.utils.ChunkSectionUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunk;

import javax.annotation.Nullable;

//<>

public class ChunkSectionCache implements AutoCloseable {
    private final IWorldReader world;
    private final Long2ObjectMap<ChunkSection> cache = new Long2ObjectOpenHashMap<>();
    @Nullable
    private ChunkSection cachedSection;
    private long sectionPos;

    public ChunkSectionCache(IWorldReader world) {
        this.world = world;
    }

    @Nullable
    public ChunkSection getSection(BlockPos pos) {
        int index = pos.getY() >> 4;
        if (index >= 0 && index < 16) {
            long sectionPos = ChunkSectionUtils.sectionToLong(pos);
            if (this.cachedSection == null || this.sectionPos != sectionPos) {
                this.cachedSection = this.cache.computeIfAbsent(sectionPos, (cache) -> {
                    IChunk chunk = this.world.getChunk(SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()));
                    ChunkSection section = ChunkSectionUtils.getSection(chunk, index);
                    section.acquire();
                    return section;
                });
                this.sectionPos = sectionPos;
            }

            return this.cachedSection;
        } else {
            return Chunk.EMPTY_SECTION;
        }
    }

    public BlockState getBlockState(BlockPos pos) {
        ChunkSection section = this.getSection(pos);
        if (section == Chunk.EMPTY_SECTION) {
            return Blocks.AIR.defaultBlockState();
        } else {
            int x = SectionPos.sectionRelative(pos.getX());
            int y = SectionPos.sectionRelative(pos.getY());
            int z = SectionPos.sectionRelative(pos.getZ());
            return section.getBlockState(x, y, z);
        }
    }

    @Override
    public void close() {
        for (ChunkSection section : this.cache.values()) {
            section.release();
        }
    }
}