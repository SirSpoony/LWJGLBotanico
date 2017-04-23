package me.spoony.botanico.server.level.levelgen.layer;

/**
 * Created by Colten on 12/22/2016.
 */
public class LayerSmooth extends Layer {

  public LayerSmooth(Layer child) {
    super(child);
  }

  @Override
  public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
    int childAreaX = areaX - 1;
    int childAreaY = areaY - 1;
    int childAreaWidth = areaWidth + 2;
    int childAreaHeight = areaHeight + 2;
    int[] childInts = this.child.getInts(childAreaX, childAreaY, childAreaWidth, childAreaHeight);
    int[] aint1 = new int[areaWidth * areaHeight];

    for (int i1 = 0; i1 < areaHeight; ++i1) {
      for (int j1 = 0; j1 < areaWidth; ++j1) {
        int k1 = childInts[j1 + 0 + (i1 + 1) * childAreaWidth];
        int l1 = childInts[j1 + 2 + (i1 + 1) * childAreaWidth];
        int i2 = childInts[j1 + 1 + (i1 + 0) * childAreaWidth];
        int j2 = childInts[j1 + 1 + (i1 + 2) * childAreaWidth];
        int k2 = childInts[j1 + 1 + (i1 + 1) * childAreaWidth];

        if (k1 == l1 && i2 == j2) {
          this.resetRand((j1 + areaX), (i1 + areaY));

          if (this.nextInt(2) == 0) {
            k2 = k1;
          } else {
            k2 = i2;
          }
        } else {
          if (k1 == l1) {
            k2 = k1;
          }

          if (i2 == j2) {
            k2 = i2;
          }
        }

        aint1[j1 + i1 * areaWidth] = k2;
      }
    }

    return aint1;
  }
}