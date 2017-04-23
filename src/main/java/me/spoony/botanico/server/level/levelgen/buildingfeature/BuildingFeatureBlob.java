package me.spoony.botanico.server.level.levelgen.buildingfeature;

import com.google.common.collect.Range;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.tiles.Tile;

import java.util.Random;

/**
 * Created by Colten on 11/13/2016.
 */
public class BuildingFeatureBlob implements BuildingFeature {

  float popularity;
  Building building;
  Tile allowedTile;
  int size;

  public BuildingFeatureBlob(float popularity, Building building, Tile allowedTile, int size) {
    this.popularity = popularity;
    this.building = building;
    this.allowedTile = allowedTile;
    this.size = size;
  }

  @Override
  public void generate(Random random, long seed, boolean[] biome, Chunk chunk) {
    for (int xi = 0; xi < Chunk.CHUNK_SIZE; xi++) {
      for (int yi = 0; yi < Chunk.CHUNK_SIZE; yi++) {
        if (biome[xi + yi * 32] == false) {
          continue;
        }

        float val = random.nextFloat();
        if (val < popularity && chunk.tiles[xi + yi * 32] == allowedTile) {
          int chainlength = size;

          if ((chunk.tiles[xi + yi * 32] == allowedTile || allowedTile == null) && (
              chunk.buildings[xi + yi * 32] == null)) {
            chunk.buildings[xi + yi * 32] = building;
          }

          for (int i = 0; i < chainlength; i++) {
            int xmod = random.nextInt(3) - 1 + xi;
            int ymod = random.nextInt(3) - 1 + yi;

            if (!Range.closed(0, 31).contains(xmod) || !Range.closed(0, 31).contains(ymod)) {
              continue;
            }

            if ((chunk.tiles[xmod + ymod * 32] == allowedTile || allowedTile == null) && (
                chunk.buildings[xmod + ymod * 32] == null)) {
              chunk.buildings[xmod + ymod * 32] = building;
            }
          }
        }
      }
    }
  }
}
