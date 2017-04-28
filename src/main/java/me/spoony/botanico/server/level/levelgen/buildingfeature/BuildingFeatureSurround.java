package me.spoony.botanico.server.level.levelgen.buildingfeature;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.level.Chunk;

/**
 * Created by Colten on 11/26/2016.
 */
public class BuildingFeatureSurround extends BuildingFeature {

  Building toSurround;
  float probability;
  Building building;
  int size;

  public BuildingFeatureSurround(Building toSurround, float probability, Building building,
      int size) {
    this.toSurround = toSurround;
    this.probability = probability;
    this.building = building;
    this.size = size;
  }

  @Override
  public void generate(long tileSeed, int x, int y, Chunk chunk) {
    initSeed(tileSeed);
    float rng = nextFloat();

    if (rng < probability && chunk.getBuilding(x, y) == toSurround) {
      for (int i = 0; i < size; i++) {
        int xmod = nextInt(3) - 1 + x;
        int ymod = nextInt(3) - 1 + y;

        if (xmod > 31 || xmod < 0 ||
            ymod > 31 || ymod < 0) {
          continue;
        }

        if (chunk.getBuilding(x, y) == null) {
          chunk.buildings[xmod + ymod * 32] = building;
        }
      }
    }
  }
}
