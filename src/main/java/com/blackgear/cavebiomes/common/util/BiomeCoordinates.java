package com.blackgear.cavebiomes.common.util;

public class BiomeCoordinates {
    public static int fromBlock(int blockCoord) {
        return blockCoord >> 2;
    }

    public static int toBlock(int biomeCoord) {
        return biomeCoord << 2;
    }

    public static int fromChunk(int coord) {
        return coord << 2;
    }

    public static int toChunk(int coord) {
        return coord >> 2;
    }
}