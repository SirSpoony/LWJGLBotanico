package me.spoony.botanico.server.level.levelgen.biome;

import me.spoony.botanico.common.buildings.Buildings;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureRandomPoint;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureSurround;

/**
 * Created by Colten on 11/26/2016.
 */
public class BiomeForest extends Biome {

  public BiomeForest(int id) {
    super(id);
    setTile(Tiles.GROUND);
/*    this.addBuildingFeature(new BuildingFeatureRandomPoint(.05f, Buildings.TREE, 3, Tiles.GROUND));
    this.addBuildingFeature(
        new BuildingFeatureSurround(Buildings.TREE, 1, Buildings.STICKS_PILE, Tiles.GROUND, 3));

    this.addBuildingFeature(new BuildingFeatureRandomPoint(.05f, Buildings.GRASS, 1, Tiles.GROUND));*/
  }
}
