package me.spoony.botanico.server.level.levelgen.biome;

import me.spoony.botanico.common.buildings.Buildings;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureBlob;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureRandomPoint;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureSurround;

/**
 * Created by Colten on 11/26/2016.
 */
public class BiomeBeach extends Biome {

  public BiomeBeach(int id) {
    super(id);

    setTile(Tiles.SAND);

    this.addBuildingFeature(new BuildingFeatureBlob(.005f, Buildings.ROCKS, 3));
    this.addBuildingFeature(new BuildingFeatureRandomPoint(.005f, 3, Buildings.BOULDER, 0));
    this.addBuildingFeature(
        new BuildingFeatureSurround(Buildings.BOULDER, 1, Buildings.ROCKS, 3));

    this.addBuildingFeature(new BuildingFeatureRandomPoint(.01f, 2, Buildings.REEDS, 0));
    this.addBuildingFeature(new BuildingFeatureRandomPoint(.01f, 2, Buildings.SEASHELL, 0));
  }
}