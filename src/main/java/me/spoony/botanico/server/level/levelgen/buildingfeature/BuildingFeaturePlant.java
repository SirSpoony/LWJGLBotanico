package me.spoony.botanico.server.level.levelgen.buildingfeature;

import com.google.common.collect.Range;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.tiles.Tile;

import java.util.Random;

/**
 * Created by Colten on 11/26/2016.
 */
public class BuildingFeaturePlant implements BuildingFeature
{
    float popularity;
    Building plant;
    int allowAdjacentsRange;
    Tile allowedTile;

    public BuildingFeaturePlant(float popularity, Building plant, int allowAdjacentsRange, Tile allowedTile)
    {
        this.popularity = popularity;
        this.plant = plant;
        this.allowAdjacentsRange = allowAdjacentsRange;
        this.allowedTile = allowedTile;
    }

    @Override
    public void generate(Random random, long seed, boolean[][] biome, Chunk chunk)
    {
        for (int xi = 0; xi < Chunk.CHUNK_SIZE; xi++)
        {
            for (int yi = 0; yi < Chunk.CHUNK_SIZE; yi++)
            {
                if (biome[xi][yi] == false) continue;

                float val = random.nextFloat();
                if (val < popularity && chunk.tiles[xi][yi] == allowedTile) {
                    if (chunk.buildings[xi][yi] != null) continue;
                    if (allowAdjacentsRange <= 0) {
                        chunk.buildings[xi][yi] = plant;
                        continue;
                    } else {
                        boolean make = true;
                        for (int xo = -allowAdjacentsRange;xo <= allowAdjacentsRange;xo++) {
                            for (int yo = -allowAdjacentsRange;yo <= allowAdjacentsRange;yo++) {
                                if (!Range.closed(0, 31).contains(xi+xo) || !Range.closed(0, 31).contains(yi+yo)) continue;
                                if (chunk.buildings[xi+xo][yi+yo] == plant) make = false;
                            }
                        }
                        if (make) chunk.buildings[xi][yi] = plant;
                    }
                }
            }
        }
    }
}
