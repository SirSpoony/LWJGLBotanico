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
public class BuildingFeatureRuin implements BuildingFeature {

  Building building;

  public BuildingFeatureRuin(Building building) {
    this.building = building;
  }

  @Override
  public void generate(Random random, long seed, boolean[] biome, Chunk chunk) {
    boolean[] clearSpace = ChunkGeneratorUnderworld
        .genClearSpace(chunk.position.x, chunk.position.y, seed);

    for (int xi = 3; xi < 28; xi++) {
      for (int yi = 3; yi < 28; yi++) {
        if (biome[xi * 32 + yi] == false) {
          continue;
        }

        if (random.nextFloat() < .01f) {
          if (clearSpace[xi * 32 + yi]) {
            boolean valid = true;
            for (int offx = -2; offx <= 2; offx++) {
              for (int offy = -2; offy <= 2; offy++) {
                if (chunk.getBuilding(xi + offx, yi + offy) != null) {
                  valid = false;
                }
              }
            }
            if (valid) {
              chunk.setBuilding(xi, yi, building);
            }
          }
        }
      }
    }
  }
}
