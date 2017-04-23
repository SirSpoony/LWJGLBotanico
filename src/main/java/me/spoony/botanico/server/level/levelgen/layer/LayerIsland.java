package me.spoony.botanico.server.level.levelgen.layer;

import java.util.Random;

/**
 * Created by Colten on 12/22/2016.
 */
public class LayerIsland extends Layer {

  long worldseed;
  Random worldrand;

  long current = 0;

  @Override
  public int nextInt() {
    return (int) nextLong();
  }

  @Override
  public int nextInt(int max) {
    return nextInt() % max;
  }

  @Override
  public long nextLong() {
    current ^= (current << 21);
    current ^= (current >>> 35);
    current ^= (current << 4);
    return current;
  }

  @Override
  public void resetRand(int x, int y) {
    current = new Random((x * 1000000000L + y) + worldseed).nextLong();
  }

  public LayerIsland(long worldseed) {
    super(null);

    this.worldseed = worldseed;
    worldrand = new Random(worldseed);
    resetRand(0, 0);
  }

  @Override
  public int[] getInts(int x, int y, int xsize, int ysize) {
    int[] ret = new int[xsize * ysize];
    resetRand(x, y);

    System.out.println("base " + xsize + " " + ysize);

    for (int i = 0; i < ret.length; i++) {
      ret[i] = nextInt(5) == 1 ? 1 : 0;
    }
    return ret;
  }
}