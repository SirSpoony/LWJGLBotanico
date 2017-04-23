package me.spoony.botanico.server.level.levelgen;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.common.util.BMath;
import me.spoony.botanico.server.level.levelgen.biome.*;

import java.util.Random;
import me.spoony.botanico.server.level.levelgen.layer.Layer;

public class ChunkGeneratorOverworld implements IChunkGenerator {

  protected long seed;
  protected Noise noise;

  public ChunkGeneratorOverworld(long seed) {
    this.seed = seed;
    noise = new Noise(256, (int) seed);
  }

  @Override
  public Chunk generateChunk(long x, long y) {
    Random rand = new Random(smear(x, y) + seed);
    Tile[] tiles = new Tile[32 * 32];
    Building[] buildings = new Building[32 * 32];
    byte[] buildingData = new byte[32 * 32];

    Layer layer = Layer.getDefaultLayers();
    int[] ints = layer.getInts((int) x * 32, (int) y * 32, 32, 32);

    for (int iy = 0; iy < 32; iy++) {
      for (int ix = 0; ix < 32; ix++) {
        int pos = ix + iy * 32;
        int intspos = ix + iy * 32;

        int currentbiome = ints[intspos];

        if (currentbiome == 1) {
          tiles[pos] = Tiles.GROUND;
        } else if (currentbiome == 3) {
          tiles[pos] = Tiles.SAND;
        } else {
          tiles[pos] = Tiles.WATER;
        }
      }
    }

    System.out.println(x + " " + y);

    return new Chunk(x, y, tiles, buildings, buildingData);
  }

  @Override
  public long getSeed() {
    return seed;
  }

  public void generateBiome(Random rand, Chunk chunk, int id, Biome biome) {

  }

  static long smear(long a, long b) {
    long hash = 17;
    hash = ((hash + a) << 5) - (hash + a);
    hash = ((hash + b) << 5) - (hash + b);
    return hash;
  }
}
