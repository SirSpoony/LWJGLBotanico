package me.spoony.botanico.server.level.levelgen.biome;

import com.google.common.collect.Lists;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.server.level.levelgen.buildingfeature.BuildingFeature;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Colten on 11/26/2016.
 */
public class Biome
{
    private ArrayList<BuildingFeature> buildingFeatures;
    private Tile tile;

    public Biome() {
        this.buildingFeatures = Lists.newArrayList();
        this.tile = Tiles.GROUND;
    }

    public void generate(Random random, long seed, boolean[][] biome, Chunk chunk) {
        for (int xi = 0; xi < 32; xi++)
        {
            for (int yi = 0; yi < 32; yi++)
            {
                if (!biome[xi][yi]) continue;
                chunk.tiles[xi][yi] = tile;
            }
        }

        for (BuildingFeature bf : buildingFeatures) {
            bf.generate(random, seed, biome, chunk);
        }
    }

    public void addBuildingFeature(BuildingFeature bf) {
        buildingFeatures.add(bf);
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
