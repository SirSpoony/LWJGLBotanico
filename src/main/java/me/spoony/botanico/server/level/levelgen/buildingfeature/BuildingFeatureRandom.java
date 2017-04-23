package me.spoony.botanico.server.level.levelgen.buildingfeature;

import com.google.common.collect.Range;
import java.util.Random;
import me.spoony.botanico.common.level.Chunk;

/**
 * Created by Colten on 4/23/2017.
 */
public class BuildingFeatureRandom implements BuildingFeature {

  protected float popularity;

  public BuildingFeatureRandom(float popularity){
    this.popularity = popularity;
  }

  @Override
  public void generate(Random random, long seed, boolean[] biome, Chunk chunk) {
    for (int xi = 0; xi < 32; xi++) {
      for (int yi = 0; yi < 32; yi++) {
        if (biome[xi + yi * 32] == false) {
          continue;
        }

        float val = random.nextFloat();
        if (val < popularity) {
          if (chunk.buildings[xi + yi * 32] != null) {
            continue;
          }

          place(xi, yi, chunk);
        }
      }
    }
  }

  public void place(int x, int y, Chunk chunk) {

  }
}
