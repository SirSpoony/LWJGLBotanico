package me.spoony.botanico.server.level.levelgen.layer;

import me.spoony.botanico.server.level.levelgen.biome.Biome;

/**
 * Created by Colten on 4/23/2017.
 */
public class LayerShore extends Layer {

  public LayerShore(Layer child) {
    super(child);
  }

  public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
    int[] childInts = this.child.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
    int[] ret = new int[areaWidth * areaHeight];

    for (int i = 0; i < areaHeight; ++i) {
      for (int j = 0; j < areaWidth; ++j) {
        this.resetRand((j + areaX), (i + areaY));
        int k = childInts[j + 1 + (i + 1) * (areaWidth + 2)];

        if (!isBiomeOceanic(k)) {
          int l1 = childInts[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
          int k2 = childInts[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
          int j3 = childInts[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
          int i4 = childInts[j + 1 + (i + 1 + 1) * (areaWidth + 2)];

          if (!isBiomeOceanic(l1) && !isBiomeOceanic(k2) &&
              !isBiomeOceanic(j3) && !isBiomeOceanic(i4)) {
            ret[j + i * areaWidth] = k;
          } else {
            ret[j + i * areaWidth] = 3; // 3 is beach
          }
        } else {
          ret[j + i * areaWidth] = k;
        }
      }
    }

    return ret;
  }

  public boolean isBiomeOceanic(int biome) {
    return biome == 0;
  }
}
