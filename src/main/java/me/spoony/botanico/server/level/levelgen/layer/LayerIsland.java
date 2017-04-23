package me.spoony.botanico.server.level.levelgen.layer;

import java.util.Random;

/**
 * Created by Colten on 12/22/2016.
 */
public class LayerIsland extends Layer {

  public LayerIsland() {
    super(null);
  }

  @Override
  public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
    int[] ret = new int[areaWidth * areaHeight];

    for (int xi = 0; xi < areaWidth; xi++) {
      for (int yi = 0; yi < areaHeight; yi++) {
        this.initChunkSeed((xi + areaX), (yi + areaY));

        ret[xi + yi * areaWidth] = nextInt(5) == 1 ? 1 : 0;
      }
    }

    if (areaX > -areaWidth && areaX <= 0 &&
        areaY > -areaHeight && areaY <= 0) {
      ret[-areaX + -areaY * areaWidth] = 1;
    }

    return ret;
  }
}