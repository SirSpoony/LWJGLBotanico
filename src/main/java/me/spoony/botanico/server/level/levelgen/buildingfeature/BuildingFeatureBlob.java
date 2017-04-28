package me.spoony.botanico.server.level.levelgen.buildingfeature;

import com.google.common.collect.Range;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.tiles.Tile;

import java.util.Random;

/**
 * Created by Colten on 11/13/2016.
 */
public class BuildingFeatureBlob extends BuildingFeature {

  float popularity;
  Building building;
  int size;

  public BuildingFeatureBlob(float popularity, Building building, int size) {
    this.popularity = popularity;
    this.building = building;
    this.size = size;
  }

  @Override
  public void generate(long tileSeed, int x, int y, Chunk chunk) {
    initSeed(tileSeed);
    float val = nextFloat();

    if (val < popularity) {
      int chainlength = size;

      if (chunk.getBuilding(x, y) == null) {
        chunk.setBuilding(x, y, building);
      }

      for (int i = 0; i < chainlength; i++) {
        int xmod = nextInt(3) - 1 + x;
        int ymod = nextInt(3) - 1 + y;

        if (xmod > 31 || xmod < 0 ||
            ymod > 31 || ymod < 0) {
          continue;
        }

        if (chunk.getBuilding(xmod, ymod) == null) {
          chunk.setBuilding(xmod, ymod, building);
        }
      }
    }
  }
}
