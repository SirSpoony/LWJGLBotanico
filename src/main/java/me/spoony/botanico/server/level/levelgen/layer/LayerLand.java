package me.spoony.botanico.server.level.levelgen.layer;

import java.util.Random;

/**
 * Created by Colten on 12/22/2016.
 */
public class LayerLand extends Layer {
    Random random;

    public LayerLand(Layer child) {
        super(child);
        this.random = new Random(child.getRandLong());
    }

    @Override
    public int[] getInts(int x, int y, int xsize, int ysize) {
        int[] childInts = child.getInts(x, y, xsize, ysize);
        int[] ret = childInts;

        for (int xi = 0; xi < xsize; xi++) {
            for (int yi = 0; yi < xsize; yi++) {
                resetRand(x + xi, y + yi);
                ret[xi + yi * ysize] += random.nextInt(2);
            }
        }
        return ret;
    }
}