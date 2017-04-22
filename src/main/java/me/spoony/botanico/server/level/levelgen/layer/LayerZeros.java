package me.spoony.botanico.server.level.levelgen.layer;

import java.util.Random;

/**
 * Created by Colten on 12/22/2016.
 */
public class LayerZeros extends Layer {

  long worldseed;
  Random worldrand;
  Random chunkrand;

  @Override
  public int getRandInt() {
    chunkrand.nextInt();
    return chunkrand.nextInt();
  }

  @Override
  public long getRandLong() {
    chunkrand.nextLong();
    return chunkrand.nextLong();
  }

  @Override
  public void resetRand(int x, int y) {
    chunkrand = new Random((x * 1000000000L + y) + worldseed);
    chunkrand.nextLong();
  }

  public LayerZeros(long worldseed) {
    super(null);

    this.worldseed = worldseed;
    worldrand = new Random(worldseed);
    chunkrand = new Random(worldrand.nextLong());
  }

  @Override
  public int[] getInts(int x, int y, int xsize, int ysize) {
    int[] ret = new int[xsize * ysize];
    for (int i = 0; i < ret.length; i++) {
      ret[i] = 0;
    }
    return ret;
  }
}