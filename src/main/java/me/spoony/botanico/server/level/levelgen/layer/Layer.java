package me.spoony.botanico.server.level.levelgen.layer;

/**
 * Created by Colten on 12/22/2016.
 *
 * Terrain is generated layer by layer. The inner-most child is the LayerIsland.
 */
public abstract class Layer {

  public Layer child;

  long baseseed = 2342342342326786788L;
  long worldseed;
  long chunkseed;

  public Layer(Layer child) {
    this.child = child;
  }

  public static Layer getDefaultLayers(long worldSeed) {
    Layer layer = new LayerIsland();
    layer = new LayerFuzzyZoom(layer);
    layer = new LayerAddIsland(layer);
    layer = new LayerZoom(layer);
    layer = new LayerAddIsland(layer);
    layer = new LayerAddIsland(layer);
    layer = new LayerSmooth(layer);
    layer = new LayerSmooth(layer);
    layer = LayerZoom.magnify(layer, 2);
    layer = new LayerSmooth(layer);
    layer = new LayerShore(layer);
    layer = LayerZoom.magnify(layer, 3);
    layer = new LayerSmooth(layer);
    layer = new LayerShore(layer); // shore should be last to ensure that there is always a shore.

    layer.initWorldGenSeed(worldSeed);

    return layer;
  }

  public abstract int[] getInts(int x, int y, int xsize, int ysize);

  /**
   * Selects a random integer from a set of provided integers
   */
  public int selectRandom(int... ints) {
    if (ints.length == 0) {
      throw new RuntimeException("size of ints is 0");
    }
    return ints[nextInt(ints.length)];
  }

  /**
   * Returns the most frequently occurring number of the set, or a random number from those provided
   */
  protected int selectModeOrRandom(int a, int b, int c, int d) {
    return b == c && c == d ? b
        : (a == b && a == c ? a
            : (a == b && a == d ? a
                : (a == c && a == d ? a
                    : (a == b && c != d ? a
                        : (a == c && b != d ? a
                            : (a == d && b != c
                                ? a
                                : (b == c && a != d
                                    ? b
                                    : (b == d && a != c
                                        ? b
                                        : (c == d && a != b
                                            ? c : this.selectRandom(
                                            new int[]{a, b, c, d}))))))))));
  }

  public int nextInt() {
    return (int) nextLong();
  }

  /**
   * Generates a random number using nextInt()
   *
   * @param max Highest value, exclusive
   * @return Random value between 0 (inclusive) and max (exclusive)
   */
  public int nextInt(int max) {
    int ret = nextInt() % max;
    if (ret < 0) {
      ret += max;
    }
    return ret;
  }

  public long nextLong() {
    chunkseed ^= (chunkseed << 21);
    chunkseed ^= (chunkseed >>> 35);
    chunkseed ^= (chunkseed << 4);
    return chunkseed;
  }

  public void initChunkSeed(long x, long y) {
    this.chunkseed = this.worldseed;
    this.chunkseed *= this.chunkseed * 6364136223846793005L + 1442695040888963407L;
    this.chunkseed += x;
    this.chunkseed *= this.chunkseed * 6364136223846793005L + 1442695040888963407L;
    this.chunkseed += y;
    this.chunkseed *= this.chunkseed * 6364136223846793005L + 1442695040888963407L;
    this.chunkseed += x;
    this.chunkseed *= this.chunkseed * 6364136223846793005L + 1442695040888963407L;
    this.chunkseed += y;
  }

  public void initWorldGenSeed(long seed) {
    this.worldseed = seed;
    this.worldseed *= this.worldseed * 6364136223846793005L + 1442695040888963407L;
    this.worldseed += this.baseseed;
    this.worldseed *= this.worldseed * 6364136223846793005L + 1442695040888963407L;
    this.worldseed += this.baseseed;
    this.worldseed *= this.worldseed * 6364136223846793005L + 1442695040888963407L;
    this.worldseed += this.baseseed;

    if (child != null) {
      child.initWorldGenSeed(seed);
    }
  }
}
