package me.spoony.botanico.common.tiles;

import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/12/2016.
 */
public class TileGroundLight extends TileConnected
{
    public TileGroundLight(int id, String name)
    {
        super(id, name);
    }

    @Override
    public void loadRenderRules()
    {
        renderRules = new TileRenderRules(new IntRectangle[]{
                new IntRectangle(128+32, 16, 16, 16),
                new IntRectangle(144+32, 16, 16, 16),
                new IntRectangle(128+32, 32, 16, 16),
                new IntRectangle(144+32, 32, 16, 16)});

        addSandRules(320, 160, Tiles.SAND, new IntRectangle(0, 48, 16, 16));
    }
}
