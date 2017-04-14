package me.spoony.botanico.server.level.levelgen.layer;

import com.google.common.math.IntMath;

/**
 * Created by Colten on 12/22/2016.
 */
public class LayerBiome extends Layer
{
    public LayerBiome(Layer child)
    {
        super(child);
    }

    @Override
    public int[] getInts(int x, int y, int xsize, int ysize)
    {
        int[] childInts = child.getInts(x, y, xsize, ysize);
        int[] ret = childInts;

        for (int xi = 0; xi < xsize; xi++)
        {
            for (int yi = 0; yi < xsize; yi++)
            {
                int arraypos = xi+yi*ysize;
                resetRand(xi+x,yi+y);

                if ((childInts[arraypos] & 1) == 1)
                {
                    ret[arraypos] += IntMath.mod(getRandInt(),3) << 1;
                }
            }
        }
        return ret;
    }
}