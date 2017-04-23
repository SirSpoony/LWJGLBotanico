package me.spoony.botanico.server.level.levelgen.biome;

import me.spoony.botanico.common.buildings.Buildings;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureBlob;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeaturePlant;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureSurround;

/**
 * Created by Colten on 1/1/2017.
 */
public class BiomeBeachEdge extends Biome {

  public BiomeBeachEdge(int id) {
    super(id);
    setTile(Tiles.GROUND_LIGHT);

    this.addBuildingFeature(new BuildingFeatureBlob(.005f, Buildings.ROCKS, Tiles.GROUND_LIGHT, 3));

    this.addBuildingFeature(
        new BuildingFeaturePlant(.005f, Buildings.BOULDER, 3, Tiles.GROUND_LIGHT));
    this.addBuildingFeature(
        new BuildingFeatureSurround(Buildings.BOULDER, 1, Buildings.ROCKS, Tiles.GROUND_LIGHT, 3));

    this.addBuildingFeature(new BuildingFeaturePlant(.05f, Buildings.GRASS, 1, Tiles.GROUND_LIGHT));
  }
}