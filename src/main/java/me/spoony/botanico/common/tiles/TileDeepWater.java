package me.spoony.botanico.common.tiles;

import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/8/2016.
 */
public class TileDeepWater extends TileConnected
{
    public TileDeepWater(int id, String name)
    {
        super(id, name);
        setShouldCollide(true);
    }

    @Override
    public void loadRenderRules()
    {
        this.renderRules = new TileRenderRules(new IntRectangle[]{
                new IntRectangle(0, 128, 16, 16)});

        addSandRules(0, 224, Tiles.WATER);
    }
}
