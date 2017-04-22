package me.spoony.botanico.server.level.levelgen.layer;

/**
 * Created by Colten on 12/22/2016.
 *
 * Terrain is generated layer by layer. The inner-most child is the LayerZeros.
 */
public abstract class Layer {

  public Layer child;

  public Layer(Layer child) {
    this.child = child;
  }

  public abstract int[] getInts(int x, int y, int xsize, int ysize);

  public void resetRand(int x, int y) {
    child.resetRand(x, y);
  }

  public int getRandInt() {
    return child.getRandInt();
  }

  public long getRandLong() {
    return child.getRandLong();
  }
}
