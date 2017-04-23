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

  public ChunkGeneratorOverworld(long seed) {
    this.seed = seed;
  }

  @Override
  public Chunk generateChunk(long x, long y) {
    Random rand = new Random(smear(x, y) + seed);
    Tile[] tiles = new Tile[32 * 32];
    Building[] buildings = new Building[32 * 32];
    byte[] buildingData = new byte[32 * 32];
    Chunk ret = new Chunk(x, y, tiles, buildings, buildingData);

    Layer layer = Layer.getDefaultLayers(seed);
    int[] ints = layer.getInts((int) x * 32, (int) y * 32, 32, 32);

    for (Biome b : Biomes.BIOME_SET) {
      boolean[] biomeMap = new boolean[32 * 32];

      for (int iy = 0; iy < 32; iy++) {
        for (int ix = 0; ix < 32; ix++) {
          int pos = ix + iy * 32;
          int currentbiome = ints[pos];

          biomeMap[pos] = (currentbiome == b.id);
        }
      }

      b.generate(rand, seed, biomeMap, ret);
    }

    return ret;
  }

  @Override
  public long getSeed() {
    return seed;
  }

  static long smear(long a, long b) {
    long hash = 17;
    hash = ((hash + a) << 5) - (hash + a);
    hash = ((hash + b) << 5) - (hash + b);
    return hash;
  }
}
