package me.spoony.botanico.server.level.levelgen.biome;

import me.spoony.botanico.common.buildings.Buildings;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureBlob;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeaturePlant;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureSuround;

/**
 * Created by Colten on 11/26/2016.
 */
public class BiomeBeach extends Biome
{
    public BiomeBeach() {
        setTile(Tiles.SAND);

        this.addBuildingFeature(new BuildingFeatureBlob(.005f, Buildings.ROCKS, Tiles.SAND, 3));


        this.addBuildingFeature(new BuildingFeaturePlant(.005f, Buildings.BOULDER, 3, Tiles.SAND));
        this.addBuildingFeature(new BuildingFeatureSuround(Buildings.BOULDER, 1, Buildings.ROCKS, Tiles.SAND, 3));
    }
}