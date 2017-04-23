package me.spoony.botanico.server.level.levelgen.layer;

/**
 * Created by Colten on 12/22/2016.
 */
public class LayerZoom extends Layer {

  public LayerZoom(Layer child) {
    super(child);
  }

  @Override
  public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
    int childAreaX = areaX >> 1;
    int childAreaY = areaY >> 1;
    int childAreaWidth = (areaWidth >> 1) + 2;
    int childAreaHeight = (areaHeight >> 1) + 2;
    int[] childInts = this.child.getInts(childAreaX, childAreaY, childAreaWidth, childAreaHeight);
    int mAreaWidth = childAreaWidth - 1 << 1; // = areaWidth + 2
    int mAreaHeight = childAreaHeight - 1 << 1; // = areaHeight + 2
    int[] mInts = new int[mAreaWidth * mAreaHeight];

    // x + (y * width)
    for (int yi = 0; yi < childAreaHeight - 1; ++yi) { // yi iterates from 0 to height-1
      int mYPos = (yi << 1) * mAreaWidth; // yi but in terms of mInts
      int xi = 0;
      int childInt = childInts[xi + 0 + (yi + 0) * childAreaWidth];

      for (int k2 = childInts[xi + 0 + (yi + 1) * childAreaWidth]; xi < childAreaWidth - 1; ++xi) {
        this.initChunkSeed((xi + childAreaX << 1), (yi + childAreaY << 1));

        int childIntRight = childInts[xi + 1 + (yi + 0) * childAreaWidth];
        int childIntDownRight = childInts[xi + 1 + (yi + 1) * childAreaWidth];
        mInts[mYPos] = childInt;
        mInts[mYPos++ + mAreaWidth] = this.selectRandom(new int[]{childInt, k2});
        mInts[mYPos] = this.selectRandom(new int[]{childInt, childIntRight});
        mInts[mYPos++ + mAreaWidth] = this
            .selectModeOrRandom(childInt, childIntRight, k2, childIntDownRight);
        childInt = childIntRight;
        k2 = childIntDownRight;
      }
    }

    int[] ret = new int[areaWidth * areaHeight];

    for (int yi = 0; yi < areaHeight; ++yi) {
      System.arraycopy(mInts, (yi + (areaY & 1)) * mAreaWidth + (areaX & 1),
          ret, yi * areaWidth, areaWidth);
    }

    return ret;
  }

  public static Layer magnify(Layer child, int times) {
    Layer ret = child;
    for (int i = 0; i < times; ++i) {
      ret = new LayerZoom(ret);
    }
    return ret;
  }
}