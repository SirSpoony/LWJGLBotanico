package me.spoony.botanico.server.level.levelgen;

import libnoiseforjava.exception.ExceptionInvalidParam;
import libnoiseforjava.module.RidgedMulti;
import libnoiseforjava.util.NoiseMap;
import libnoiseforjava.util.NoiseMapBuilderPlane;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.buildings.Buildings;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.common.util.BMath;
import me.spoony.botanico.common.util.position.ChunkPosition;

import java.util.Random;

/**
 * Created by Colten on 1/1/2017.
 */
public class ChunkGeneratorUnderworld implements IChunkGenerator {
    long seed;

    public ChunkGeneratorUnderworld(long seed) {
        this.seed = seed;
    }

    @Override
    public Chunk generateChunk(ChunkPosition position) {
        Random rand = new Random(BMath.smear(position.x, position.y) + seed);

        long chunkx = position.x;
        long chunky = position.y;

        boolean[][] clearSpace = genClearSpace(chunkx, chunky, getSeed());

        Building[][] buildings = new Building[32][32];
        Tile[][] tiles = new Tile[32][32];
        byte[][] buildingData = new byte[32][32];

        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                buildings[x][y] = null;
                tiles[x][y] = clearSpace[x][y] ? Tiles.CAVE_FLOOR : Tiles.CAVE_WALL;
                if (clearSpace[x][y]) {
                    float prob = rand.nextFloat();
                    if (prob <.06f) {
                        buildings[x][y] = Buildings.BOULDER;
                    }
                    if (prob <.02f) {
                        buildings[x][y] = Buildings.COPPER_ORE;
                    }
                    if (prob <.01f) {
                        buildings[x][y] = Buildings.COAL_ORE;
                    }
                }
            }
        }
        return new Chunk(position, tiles, buildings, buildingData);
    }

    @Override
    public long getSeed() {
        return seed;
    }

    public static boolean[][] genClearSpace(long cx, long cy, long seed) {
        try {
            RidgedMulti ridgedMulti = new RidgedMulti();
            ridgedMulti.setOctaveCount(1);
            ridgedMulti.setFrequency(1);
            ridgedMulti.setSeed((int)seed);

            // create Noisemap object
            NoiseMap heightMap = new NoiseMap(32, 32);

            // create Builder object
            NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
            heightMapBuilder.setSourceModule(ridgedMulti);
            heightMapBuilder.setDestNoiseMap(heightMap);
            heightMapBuilder.setDestSize(32, 32);
            heightMapBuilder.setBounds(cx * 3, cx * 3 + 3, cy * 3, cy * 3 + 3);
            heightMapBuilder.build();

            boolean[][] ret = new boolean[32][32];
            for (int x = 0; x < 32; x++) {
                for (int y = 0; y < 32; y++) {
                    ret[x][y] = heightMap.getValue(x, y) > -0.4d ? true : false;
                }
            }
            return ret;
        } catch (ExceptionInvalidParam exceptionInvalidParam) {
            exceptionInvalidParam.printStackTrace();
        }
        return new boolean[32][32];
    }
}
