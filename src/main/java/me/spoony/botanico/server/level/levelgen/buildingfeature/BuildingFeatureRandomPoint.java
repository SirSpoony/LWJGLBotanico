package me.spoony.botanico.server.level.levelgen.buildingfeature;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.level.Chunk;

/**
 * Created by Colten on 11/26/2016.
 */
public class BuildingFeatureRandomPoint extends BuildingFeature {

  public float popularity;
  public int clearRange;

  public Building building;
  public int data;

  public BuildingFeatureRandomPoint(float popularity, int allowAdjacentsRange, Building building, int data) {
    this.popularity = popularity;
    this.clearRange = allowAdjacentsRange;

    this.building = building;
    this.data = data;
  }

  @Override
  public void generate(long tileSeed, int x, int y, Chunk chunk) {
    initSeed(tileSeed);
    float val = nextFloat();

    if (val < popularity) {
      if (clearRange <= 0) {
        chunk.setBuilding(x, y, building, data);
        return;
      }

      for (int xo = -clearRange; xo <= clearRange; xo++) {
        for (int yo = -clearRange; yo <= clearRange; yo++) {
          if (x + xo > 31 || x + xo < 0 ||
              y + yo > 31 || y + yo < 0) {
            continue;
          }

          if (chunk.getBuilding(x + xo, y + yo) != null) {
            return;
          }
        }
      }

      chunk.setBuilding(x, y, building, data);
    }
  }
}
