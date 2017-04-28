package me.spoony.botanico.server.level.levelgen.biome;

import me.spoony.botanico.common.buildings.Buildings;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureCave;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureRandomPoint;

/**
 * Created by Colten on 11/26/2016.
 */
public class BiomePrairie extends Biome {

  public BiomePrairie(int id) {
    super(id);

    this.setTile(Tiles.GROUND);

    //this.addBuildingFeature(new BuildingFeatureBlob(.005f, Buildings.DIRT_MOUND, Tiles.GROUND, 3));

    this.addBuildingFeature(new BuildingFeatureRandomPoint(.05f, 3, Buildings.TREE, 0));

    this.addBuildingFeature(new BuildingFeatureRandomPoint(.01f, 2, Buildings.FLOWER, 0));
    this.addBuildingFeature(new BuildingFeatureRandomPoint(.01f, 2, Buildings.FLOWER, 1));
    this.addBuildingFeature(new BuildingFeatureRandomPoint(.01f, 2, Buildings.FLOWER, 2));
    this.addBuildingFeature(new BuildingFeatureRandomPoint(.01f, 2, Buildings.FLOWER, 3));

    this.addBuildingFeature(new BuildingFeatureRandomPoint(.05f, 0, Buildings.GRASS, 0));

    this.addBuildingFeature(new BuildingFeatureRandomPoint(.01f, 1, Buildings.BUSH, 1));
    this.addBuildingFeature(new BuildingFeatureRandomPoint(.01f, 0, Buildings.SMALL_BUSH, 0));

    this.addBuildingFeature(new BuildingFeatureCave(Buildings.CAVE));
  }
}
