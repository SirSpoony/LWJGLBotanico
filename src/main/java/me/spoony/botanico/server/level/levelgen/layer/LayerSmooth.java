package me.spoony.botanico.server.level.levelgen.layer;

/**
 * Created by Colten on 12/22/2016.
 */
public class LayerSmooth extends Layer {
    public LayerSmooth(Layer child) {
        super(child);
    }

    @Override
    public int[] getInts(int x, int y, int xsize, int ysize) {
        int childsize = xsize*2;
        int[] childInts = child.getInts(x, y, childsize, childsize);
        int[] ret = new int[xsize*ysize];

        for (int xi = 0; xi < xsize; xi++) {
            for (int yi = 0; yi < xsize; yi++) {
                int northCheck = childInts[xi + 1 + (yi + 0) * childsize];
                int southCheck = childInts[xi + 1 + (yi + 2) * childsize];
                int eastCheck = childInts[xi + 2 + (yi + 1) * childsize];
                int westCheck = childInts[xi + 0 + (yi + 1) * childsize];

                int centerCheck = childInts[xi + 1 + (yi + 1) * childsize];

                if (westCheck == eastCheck && northCheck == southCheck) {
                    if ((getRandInt() & 1) == 0)
                        centerCheck = westCheck;
                    else
                        centerCheck = northCheck;
                } else {
                    if (westCheck == eastCheck)
                        centerCheck = westCheck;

                    if (northCheck == southCheck)
                        centerCheck = northCheck;
                }

                ret[xi + yi * xsize] = centerCheck;
            }
        }

        return ret;
    }
}