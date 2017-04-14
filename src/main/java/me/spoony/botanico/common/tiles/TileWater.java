package me.spoony.botanico.common.tiles;

import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/8/2016.
 */
public class TileWater extends TileConnected
{
    public TileWater(int id, String name)
    {
        super(id, name);
        this.footStepMaterial = FootStepMaterial.WATER;
    }

    @Override
    public void loadRenderRules()
    {
        this.renderRules = new TileRenderRules(new IntRectangle[]{
                new IntRectangle(0, 96, 16, 16)});

        addSandRules(0, 160, Tile.REGISTRY.get("sand"));
    }
}
