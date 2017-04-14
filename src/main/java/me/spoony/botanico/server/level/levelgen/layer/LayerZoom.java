package me.spoony.botanico.server.level.levelgen.layer;

import com.google.common.math.IntMath;
import me.spoony.botanico.common.util.BMath;

/**
 * Created by Colten on 12/22/2016.
 */
public class LayerZoom extends Layer {
    boolean offset;

    public LayerZoom(Layer child, boolean offset) {
        super(child);
        this.offset = offset;
    }

    @Override
    public int[] getInts(int x, int y, int xsize, int ysize) {
        int childxsize = xsize;
        int childysize = ysize;

        int[] childInts = child.getInts(x / 2, y / 2, childxsize, childysize);
        int[] ret = new int[xsize * ysize];

        for (int xi = 0; xi < xsize; xi++) {
            for (int yi = 0; yi < xsize; yi++) {
                resetRand(x + xi, y + yi);

                int a = childInts[(xi / 2 + 0) + ((yi / 2 + 0) * childysize)];
                int b = childInts[(xi / 2 + 1) + ((yi / 2 + 0) * childysize)];
                int c = childInts[(xi / 2 + 0) + ((yi / 2 + 1) * childysize)];
                int d = childInts[(xi / 2 + 1) + ((yi / 2 + 1) * childysize)];
                if (offset) {
                    ret[xi + yi * ysize] = getRandomOf4(getRandLong(), a, b, c, d);
                    //ret[xi + yi * ysize] = getRandomInArray(rand,a,b,c,d);
                } else {
                    ret[xi + yi * ysize] = getRandomInArray(getRandLong(), a);
                }
            }
        }

        return ret;
    }

    protected int getRandomInArray(long rand, int... biomes) {
        return biomes[IntMath.mod((int) BMath.scramble(rand), biomes.length)];
    }

    protected int getRandomOf4(long rand, int par1, int par2, int par3, int par4) {
        if (par2 == par3 && par3 == par4) {
            return par2;
        } else if (par1 == par2 && par1 == par3) {
            return par1;
        } else if (par1 == par2 && par1 == par4) {
            return par1;
        } else if (par1 == par3 && par1 == par4) {
            return par1;
        } else if (par1 == par2 && par3 != par4) {
            return par1;
        } else if (par1 == par3 && par2 != par4) {
            return par1;
        } else if (par1 == par4 && par2 != par3) {
            return par1;
        } else if (par2 == par1 && par3 != par4) {
            return par2;
        } else if (par2 == par3 && par1 != par4) {
            return par2;
        } else if (par2 == par4 && par1 != par3) {
            return par2;
        } else if (par3 == par1 && par2 != par4) {
            return par3;
        } else if (par3 == par2 && par1 != par4) {
            return par3;
        } else if (par3 == par4 && par1 != par2) {
            return par3;
        } else if (par4 == par1 && par2 != par3) {
            return par3;
        } else if (par4 == par2 && par1 != par3) {
            return par3;
        } else if (par4 == par3 && par1 != par2) {
            return par3;
        } else {
            int var5 = IntMath.mod((int) BMath.scramble(rand), 4);
            return var5 == 0 ? par1 : (var5 == 1 ? par2 : (var5 == 2 ? par3 : par4));
        }
    }
}