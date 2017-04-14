package me.spoony.botanico.server.level.levelgen.layer;

import me.spoony.botanico.common.util.BMath;

/**
 * Created by Colten on 12/22/2016.
 */
public class LayerZoomVoronoi extends Layer
{
    int zoom;

    public LayerZoomVoronoi(Layer child, int zoom)
    {
        super(child);
        this.zoom = zoom;
    }

    @Override
    public int[] getInts(int x, int y, int xsize, int ysize)
    {
        int childxsize = xsize / zoom;
        int childysize = ysize / zoom;

        int[] childInts = child.getInts(x/zoom, y/zoom, childxsize, childysize);

        int[] ret = new int[xsize * ysize];

        for (int xi = 0; xi < xsize; xi++)
        {
            for (int yi = 0; yi < xsize; yi++)
            {
                int floorchildx = xi / zoom;
                int floorchildy = yi / zoom;
                float childx = ((float) xi / (float) zoom);
                float childy = ((float) yi / (float) zoom);

                int nearestx = 0;
                int nearesty = 0;
                float nearestdist = Float.MAX_VALUE;

                for (int offx = -1; offx <= 1; offx++)
                {
                    for (int offy = -1; offy <= 1; offy++)
                    {
                        long hashed = BMath.smear(BMath.scramble(offx + floorchildx), offy + floorchildy);
                        float voronoipointx = Math.abs(((float) BMath.scramble(hashed)) / Long.MAX_VALUE) + (float) offx + floorchildx;
                        float voronoipointy = Math.abs(((float) BMath.scramble(hashed)) / Long.MAX_VALUE) + (float) offy + floorchildy;
                        float xdist = childx - voronoipointx;
                        float ydist = childy - voronoipointy;
                        float currentdist = Math.abs(xdist * xdist) + Math.abs(ydist * ydist);

                        if (currentdist < nearestdist)
                        {
                            //System.out.println(voronoipointx);

                            nearestdist = currentdist;
                            nearestx = offx + floorchildx;
                            nearesty = offy + floorchildy;
                        }
                    }
                }

                //System.out.println(nearestx);
                ret[xi + yi * ysize] = childInts[BMath.clamp(nearestx + nearesty * childysize, 0, childInts.length - 1)];
            }
        }

        return ret;
    }
}