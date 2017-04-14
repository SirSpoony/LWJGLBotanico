package me.spoony.botanico.server.level.levelgen.biome;

import me.spoony.botanico.common.buildings.Buildings;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureBlob;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureCave;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeaturePlant;

/**
 * Created by Colten on 11/26/2016.
 */
public class BiomePrairie extends Biome
{
    public BiomePrairie() {
        this.setTile(Tiles.GROUND);

        this.addBuildingFeature(new BuildingFeatureBlob(.005f, Buildings.DIRT_MOUND, Tiles.GROUND, 3));

        this.addBuildingFeature(new BuildingFeatureCave());

        this.addBuildingFeature(new BuildingFeaturePlant(.01f, Buildings.FLOWER_ORANGE, 2, Tiles.GROUND));
        this.addBuildingFeature(new BuildingFeaturePlant(.01f, Buildings.FLOWER_RED, 2, Tiles.GROUND));
        this.addBuildingFeature(new BuildingFeaturePlant(.3f, Buildings.GRASS, 0, Tiles.GROUND));
    }
}
