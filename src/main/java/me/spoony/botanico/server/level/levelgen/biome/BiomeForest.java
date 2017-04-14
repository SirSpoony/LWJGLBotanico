package me.spoony.botanico.server.level.levelgen.biome;

import me.spoony.botanico.common.buildings.Buildings;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeaturePlant;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeatureSuround;

/**
 * Created by Colten on 11/26/2016.
 */
public class BiomeForest extends Biome
{
    public BiomeForest() {
        setTile(Tiles.GROUND);
        this.addBuildingFeature(new BuildingFeaturePlant(.05f, Buildings.TREE, 3, Tiles.GROUND));
        this.addBuildingFeature(new BuildingFeatureSuround(Buildings.TREE, 1, Buildings.STICKS_PILE, Tiles.GROUND, 3));

        this.addBuildingFeature(new BuildingFeaturePlant(.05f, Buildings.GRASS, 1, Tiles.GROUND));
    }
}
