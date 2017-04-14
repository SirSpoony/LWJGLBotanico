package me.spoony.botanico.server.level.levelgen.buildingfeature;

import com.google.common.collect.Range;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.buildings.Buildings;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.server.level.levelgen.ChunkGeneratorUnderworld;

import java.util.Random;

/**
 * Created by Colten on 1/2/2017.
 */
public class BuildingFeatureCave implements BuildingFeature {
    public BuildingFeatureCave() {

    }

    @Override
    public void generate(Random random, long seed, boolean[][] biome, Chunk chunk) {
        boolean[][] clearSpace = ChunkGeneratorUnderworld.genClearSpace(chunk.position.x, chunk.position.y, seed);

        for (int xi = 3; xi < 28; xi++) {
            for (int yi = 3; yi < 28; yi++) {
                if (biome[xi][yi] == false) continue;

                if (random.nextFloat() < .01f) {
                    if (clearSpace[xi][yi]) {
                        boolean valid = true;
                        for (int offx = -3; offx <= 3; offx++) {
                            for (int offy = -3; offy <= 3; offy++) {
                                if (chunk.getBuilding(xi+offx, yi+offy) == Buildings.CAVE) valid = false;
                            }
                        }
                        if (valid) {
                            chunk.setBuilding(xi, yi, Buildings.CAVE);
                        }
                    }
                }
            }
        }
    }
}
