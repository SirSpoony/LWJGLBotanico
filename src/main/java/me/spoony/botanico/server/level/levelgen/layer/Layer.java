package me.spoony.botanico.server.level.levelgen.layer;

/**
 * Created by Colten on 12/22/2016.
 *
 * Terrain is generated layer by layer. The inner-most child is the LayerIsland.
 */
public abstract class Layer {

  public Layer child;

  public static Layer getDefaultLayers() {
    Layer layer = new LayerIsland(11);
    layer = new LayerFuzzyZoom(layer);
    layer = new LayerAddIsland(layer);
    layer = new LayerZoom(layer);
    layer = new LayerAddIsland(layer);
    layer = new LayerAddIsland(layer);
    layer = new LayerSmooth(layer);
    layer = LayerZoom.magnify(layer, 3);
    layer = new LayerShore(layer);
    layer = LayerZoom.magnify(layer, 3);
    layer = new LayerSmooth(layer);

    return layer;
  }

  public Layer(Layer child) {
    this.child = child;
  }

  public abstract int[] getInts(int x, int y, int xsize, int ysize);

  public void resetRand(int x, int y) {
    child.resetRand(x, y);
  }

  public int nextInt() {
    return child.nextInt();
  }

  /**
   * Generates a random number using nextInt()
   *
   * @param max Highest value, exclusive
   * @return Random value between 0 (inclusive) and max (exclusive)
   */
  public int nextInt(int max) {
    int ret = child.nextInt() % max;
    if (ret < 0) {
      ret += max;
    }
    return ret;
  }

  public long nextLong() {
    return child.nextLong();
  }


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
}
