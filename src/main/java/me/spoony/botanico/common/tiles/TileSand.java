package me.spoony.botanico.common.tiles;

import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/8/2016.
 */
public class TileSand extends Tile
{
    public TileSand(int id, String name)
    {
        super(id, name);
        this.footStepMaterial = FootStepMaterial.SAND;
    }

    @Override
    public void loadRenderRules()
    {
        renderRules = new TileRenderRules(new IntRectangle[]{
                new IntRectangle(0, 48, 16, 16)});
    }
}
