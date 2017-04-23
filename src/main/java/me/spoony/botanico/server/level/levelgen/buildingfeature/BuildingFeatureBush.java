package me.spoony.botanico.server.level.levelgen.buildingfeature;

import java.util.Random;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.level.Chunk;

/**
 * Created by Colten on 4/23/2017.
 */
public class BuildingFeatureBush extends BuildingFeatureRandom {

  protected Building bush;

  public BuildingFeatureBush(float popularity, Building bush) {
    super(popularity);

    this.bush = bush;
  }

  @Override
  public void place(int x, int y, Chunk chunk) {
    chunk.setBuilding(x, y, bush);
  }
}
