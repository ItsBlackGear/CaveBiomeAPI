package com.blackgear.cavebiomes.core.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunk;

//<>

public class ChunkSectionUtils {
    public static long sectionToLong(BlockPos pos) {
        return SectionPos.asLong(SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getY()), SectionPos.blockToSectionCoord(pos.getZ()));
    }

    public static ChunkSection getSection(IChunk chunk, int yIndex) {
        ChunkSection[] sections = chunk.getSections();
        if (sections[yIndex] == Chunk.EMPTY_SECTION) {
            sections[yIndex] = new ChunkSection(yIndex << 4);
        }

        return sections[yIndex];
    }
}