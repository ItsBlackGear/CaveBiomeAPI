package com.blackgear.cavebiomes.common.world.feature;

import com.blackgear.cavebiomes.common.world.ChunkSectionCache;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import java.util.BitSet;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

//<>

public class CaveOreFeature extends Feature<CaveOreFeatureConfig> {
    public CaveOreFeature(Codec<CaveOreFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, CaveOreFeatureConfig config) {
        float chance = rand.nextFloat() * (float)Math.PI;
        float size = config.size / 8.0F;
        int density = MathHelper.ceil((config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double startX = pos.getX() + Math.sin(chance) * size;
        double endX = pos.getX() - Math.sin(chance) * size;
        double startZ = pos.getZ() + Math.cos(chance) * size;
        double endZ = pos.getZ() - Math.cos(chance) * size;
        double startY = pos.getY() + rand.nextInt(3) - 2;
        double endY = pos.getY() + rand.nextInt(3) - 2;
        int x = pos.getX() - MathHelper.ceil(size) - density;
        int y = pos.getY() - 2 - density;
        int z = pos.getZ() - MathHelper.ceil(size) - density;
        int horizontalSize = 2 * (MathHelper.ceil(size) + density);
        int verticalSize = 2 * (2 + density);


        for (int xSize = x; xSize <= x + horizontalSize; xSize++) {
            for (int zSize = z; zSize <= z + horizontalSize; zSize++) {
                if (y <= reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, xSize, zSize)) {
                    return this.generateVeinPart(reader, rand, config, startX, endX, startZ, endZ, startY, endY, x, y, z, horizontalSize, verticalSize);
                }
            }
        }

        return false;
    }

    protected boolean generateVeinPart(IWorldReader world, Random rand, CaveOreFeatureConfig config, double startX, double endX, double startZ, double endZ, double startY, double endY, int x, int y, int z, int horizontalSize, int verticalSize) {
        int index = 0;
        BitSet radius = new BitSet(horizontalSize * verticalSize * horizontalSize);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int maxSize = config.size;
        double[] totalDensity = new double[maxSize * 4];

        int size;
        double xDensity;
        double yDensity;
        double zDensity;
        double noise;
        for(size = 0; size < maxSize; ++size) {
            float density = (float)size / (float)maxSize;
            xDensity = MathHelper.lerp(density, startX, endX);
            yDensity = MathHelper.lerp(density, startY, endY);
            zDensity = MathHelper.lerp(density, startZ, endZ);
            noise = rand.nextDouble() * (double)maxSize / 16.0D;
            double noiseDensity = ((double)(MathHelper.sin((float)Math.PI * density) + 1.0F) * noise + 1.0D) / 2.0D;
            totalDensity[size * 4] = xDensity;
            totalDensity[size * 4 + 1] = yDensity;
            totalDensity[size * 4 + 2] = zDensity;
            totalDensity[size * 4 + 3] = noiseDensity;
        }

        int size1;
        for(size = 0; size < maxSize - 1; ++size) {
            if (!(totalDensity[size * 4 + 3] <= 0.0D)) {
                for(size1 = size + 1; size1 < maxSize; ++size1) {
                    if (!(totalDensity[size1 * 4 + 3] <= 0.0D)) {
                        xDensity = totalDensity[size * 4] - totalDensity[size1 * 4];
                        yDensity = totalDensity[size * 4 + 1] - totalDensity[size1 * 4 + 1];
                        zDensity = totalDensity[size * 4 + 2] - totalDensity[size1 * 4 + 2];
                        noise = totalDensity[size * 4 + 3] - totalDensity[size1 * 4 + 3];
                        if (noise * noise > xDensity * xDensity + yDensity * yDensity + zDensity * zDensity) {
                            if (noise > 0.0D) {
                                totalDensity[size1 * 4 + 3] = -1.0D;
                            } else {
                                totalDensity[size * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        ChunkSectionCache sectionCache = new ChunkSectionCache(world);

        try {
            for(size1 = 0; size1 < maxSize; ++size1) {
                xDensity = totalDensity[size1 * 4 + 3];
                if (!(xDensity < 0.0D)) {
                    yDensity = totalDensity[size1 * 4];
                    zDensity = totalDensity[size1 * 4 + 1];
                    noise = totalDensity[size1 * 4 + 2];
                    int fromX = Math.max(MathHelper.floor(yDensity - xDensity), x);
                    int fromY = Math.max(MathHelper.floor(zDensity - xDensity), y);
                    int fromZ = Math.max(MathHelper.floor(noise - xDensity), z);
                    int toX = Math.max(MathHelper.floor(yDensity + xDensity), fromX);
                    int toY = Math.max(MathHelper.floor(zDensity + xDensity), fromY);
                    int toZ = Math.max(MathHelper.floor(noise + xDensity), fromZ);

                    for(int xArea = fromX; xArea <= toX; ++xArea) {
                        double xSize = ((double)xArea + 0.5D - yDensity) / xArea;
                        if (xSize * xSize < 1.0D) {
                            for(int yArea = fromY; yArea <= toY; ++yArea) {
                                double ySize = ((double)yArea + 0.5D - zDensity) / xArea;
                                if (xSize * xSize + ySize * ySize < 1.0D) {
                                    for(int zArea = fromZ; zArea <= toZ; ++zArea) {
                                        double zSize = ((double)zArea + 0.5D - noise) / xArea;
                                        if (xSize * xSize + ySize * ySize + zSize * zSize < 1.0D && !World.isOutsideBuildHeight(yArea)) {
                                            int totalSize = xArea - x + (yArea - y) * horizontalSize + (zArea - z) * horizontalSize * verticalSize;
                                            if (!radius.get(totalSize)) {
                                                radius.set(totalSize);
                                                mutable.set(xArea, yArea, zArea);
                                                ChunkSection chunkSection = sectionCache.getSection(mutable);
                                                if (chunkSection != Chunk.EMPTY_SECTION) {
                                                    int xIn = SectionPos.sectionRelative(xArea);
                                                    int yIn = SectionPos.sectionRelative(yArea);
                                                    int zIn = SectionPos.sectionRelative(zArea);
                                                    BlockState state = chunkSection.getBlockState(xIn, yIn, zIn);

                                                    for (CaveOreFeatureConfig.Target target : config.targets) {
                                                        Objects.requireNonNull(sectionCache);
                                                        if (shouldPlace(state, sectionCache::getBlockState, rand, config, target, mutable)) {
                                                            chunkSection.setBlockState(xIn, yIn, zIn, target.state, false);
                                                            ++index;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable throwable) {
            try {
                sectionCache.close();
            } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
            }

            throw throwable;
        }

        sectionCache.close();
        return index > 0;
    }

    public static boolean shouldPlace(BlockState state, Function<BlockPos, BlockState> posToState, Random rand, CaveOreFeatureConfig config, CaveOreFeatureConfig.Target target, BlockPos.Mutable pos) {
        if (!target.target.test(state, rand)) {
            return false;
        } else if (shouldNotDiscard(rand, config.discardChanceOnAirExposure)) {
            return true;
        } else {
            return !FeatureUtils.isExposedToAir(posToState, pos);
        }
    }

    protected static boolean shouldNotDiscard(Random rand, float chance) {
        if (chance <= 0.0F) {
            return true;
        } else if (chance >= 1.0F) {
            return false;
        } else {
            return rand.nextFloat() >= chance;
        }
    }
}