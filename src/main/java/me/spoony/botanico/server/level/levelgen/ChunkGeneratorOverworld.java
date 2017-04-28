package me.spoony.botanico.server.level.levelgen;

import java.util.Random;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.server.level.levelgen.biome.*;

import me.spoony.botanico.server.level.levelgen.buildingfeature.IBuildingFeature;
import me.spoony.botanico.server.level.levelgen.layer.Layer;

public class ChunkGeneratorOverworld implements IChunkGenerator {

  protected long overworldSeed;
  protected Layer layers;

  public ChunkGeneratorOverworld(long seed) {
    this.initOverworldSeed(seed);
    this.layers = Layer.getDefaultLayers(overworldSeed);
  }

  @Override
  public Chunk generateChunk(long x, long y) {
    Tile[] tiles = new Tile[32 * 32];
    Building[] buildings = new Building[32 * 32];
    int[] buildingData = new int[32 * 32];

    Chunk chunk = new Chunk(x, y, tiles, buildings, buildingData);

    long chunkSeed = initChunkSeed(x, y);
    int[] ints = layers.getInts((int) x * 32, (int) y * 32, 32, 32);

    long currentSeed = nextSeed(chunkSeed);

    for (Biome b : Biomes.BIOME_SET) {
      for (int iy = 0; iy < 32; iy++) {
        for (int ix = 0; ix < 32; ix++) {
          int pos = ix + iy * 32;
          int currentbiome = ints[pos];

          if (currentbiome == b.id) {
            chunk.setTile(ix, iy, b.tile);
            for (IBuildingFeature bf : b.buildingFeatures) {
              currentSeed = nextSeed(currentSeed);
              bf.generate(currentSeed, ix, iy, chunk);
            }
          }
        }
      }
    }

    return chunk;
  }

  @Override
  public long getOverworldSeed() {
    return overworldSeed;
  }

  public void initOverworldSeed(long seed) {
    this.overworldSeed = new Random(seed).nextLong();
  }

  public long initChunkSeed(long chunkX, long chunkY) {
    long seed = getOverworldSeed();
    seed *= seed * 6364136223846793005L + 1442695040888963407L;
    seed += chunkX;
    seed *= seed * 6364136223846793005L + 1442695040888963407L;
    seed += chunkY;
    seed *= seed * 6364136223846793005L + 1442695040888963407L;
    seed += chunkX;
    seed *= seed * 6364136223846793005L + 1442695040888963407L;
    seed += chunkY;
    return seed;
  }

  public long nextSeed(long seed) {
    seed ^= (seed << 21);
    seed ^= (seed >>> 35);
    seed ^= (seed << 4);
    return seed;
  }
}
