package me.spoony.botanico.server.level.levelgen.buildingfeature;

/**
 * Created by Colten on 4/26/2017.
 */
public abstract class BuildingFeature implements IBuildingFeature {

  long seed;

  public void initSeed(long seed) {
    this.seed = seed;
  }

  public long nextLong() {
    seed ^= (seed << 21);
    seed ^= (seed >>> 35);
    seed ^= (seed << 4);
    return seed;
  }

  public int nextInt() {
    return (int) nextLong();
  }

  public int nextInt(int max) {
    int ret = nextInt() % max;
    if (ret < 0) {
      ret += max;
    }
    return ret;
  }

  public float nextFloat() {
    float ret = Math.abs((float) nextLong() / Long.MAX_VALUE);
    return ret;
  }
}
