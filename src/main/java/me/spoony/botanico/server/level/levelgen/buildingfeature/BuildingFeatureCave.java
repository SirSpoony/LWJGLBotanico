package me.spoony.botanico.server.level.levelgen.buildingfeature;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.server.level.levelgen.ChunkGeneratorUnderworld;

import java.util.Random;

/**
 * Created by Colten on 1/2/2017.
 */
public class BuildingFeatureCave implements IBuildingFeature {

  Building building;

  public BuildingFeatureCave(Building building) {
    this.building = building;
  }

  @Override
  public void generate(long worldSeed, int x, int y, Chunk chunk) {
    /*boolean[] clearSpace = ChunkGeneratorUnderworld
        .genClearSpace(chunk.x, chunk.y, seed);

    for (int xi = 3; xi < 28; xi++) {
      for (int yi = 3; yi < 28; yi++) {
        if (biome[xi  + yi* 32] == false) {
          continue;
        }

        if (random.nextFloat() < .01f) {
          if (clearSpace[xi  + yi* 32]) {
            boolean valid = true;
            for (int offx = -1; offx <= 1; offx++) {
              for (int offy = -1; offy <= 1; offy++) {
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
    }*/
  }
}
