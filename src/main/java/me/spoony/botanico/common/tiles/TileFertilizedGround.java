package me.spoony.botanico.common.tiles;

import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/27/2016.
 */
public class TileFertilizedGround extends TileConnected
{
    public TileFertilizedGround(int id, String name)
    {
        super(id, name);
    }

    @Override
    public void loadRenderRules()
    {
        renderRules = new TileRenderRules(new IntRectangle[]{
                new IntRectangle(128, 16, 16, 16),
                new IntRectangle(144, 16, 16, 16),
                new IntRectangle(128, 32, 16, 16),
                new IntRectangle(144, 32, 16, 16)});
    }
}
