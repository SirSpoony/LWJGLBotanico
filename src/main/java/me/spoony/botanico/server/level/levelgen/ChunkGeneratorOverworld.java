package me.spoony.botanico.server.level.levelgen;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.util.BMath;
import me.spoony.botanico.common.util.position.ChunkPosition;
import me.spoony.botanico.server.level.levelgen.biome.*;

import java.util.Random;

public class ChunkGeneratorOverworld implements IChunkGenerator {
    protected long seed;
    protected Noise noise;

    public ChunkGeneratorOverworld(long seed) {
        this.seed = seed;
        noise = new Noise(256, (int)seed);
    }

    public Chunk generateChunk(ChunkPosition position) {
        Random rand = new Random(smear(position.x, position.y) + seed);
        Tile[][] tiles = new Tile[32][32];
        Building[][] buildings = new Building[32][32];
        byte[][] buildingData = new byte[32][32];

        Chunk chunk = new Chunk(new ChunkPosition(position), tiles, buildings, buildingData);

        generateBiome(rand, chunk, 0, new BiomeLake());
        generateBiome(rand, chunk, 1, new BiomePrairie());
        generateBiome(rand, chunk, 2, new BiomeDeepLake());
        generateBiome(rand, chunk, 12, new BiomeBeach());
        generateBiome(rand, chunk, 13, new BiomeBeachEdge());
        generateBiome(rand, chunk, 24, new BiomeForest());

        return chunk;
    }

    @Override
    public long getSeed() {
        return seed;
    }

    public void generateBiome(Random rand, Chunk chunk, int id, Biome biome) {

        boolean[][] biomemap = new boolean[32][32];
        for (int xi = 0; xi < 32; xi++) {
            for (int yi = 0; yi < 32; yi++) {
                long xireal = xi + chunk.position.x * Chunk.CHUNK_SIZE;
                long yireal = yi + chunk.position.y * Chunk.CHUNK_SIZE;

                if (getBiome(xireal, yireal) == id) {
                    biomemap[xi][yi] = true;
                } else {
                    biomemap[xi][yi] = false;
                }
            }
        }
        biome.generate(rand, getSeed(), biomemap, chunk);
    }

    public int getBiome(long x, long y) {
        float softaltitude = noise.octavePerlin(x / 128f, y / 128f, 0f, 4, .5f);
        float altitude = noise.octavePerlin(x / 128f, y / 128f, 0f, 8, .5f);
        float temperature = noise.octavePerlin(x / 128f, y / 128f, 0f, 6, .5f);
        temperature = BMath.lerp(altitude, temperature, .5f);

        int ret = 2;
        if (softaltitude > .36) {
            ret = 0;
            if (softaltitude > .4) {
                // sand
                ret = 12;
                if (softaltitude > .43) {
                    ret = 13;
                    if (softaltitude > .48) {
                        //land
                        ret = 1;
                        if (temperature > .6f) {
                            ret = 24;
                        }
                    }
                }
            }
        }

        return ret;
    }

    static long smear(long a, long b) {
        long hash = 17;
        hash = ((hash + a) << 5) - (hash + a);
        hash = ((hash + b) << 5) - (hash + b);
        return hash;
    }
}
