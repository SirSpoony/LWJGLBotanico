package me.spoony.botanico.common.tiles;

import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/8/2016.
 */
public class TileGround extends TileConnected {
    public TileGround(int id, String name) {
        super(id, name);
    }

    @Override
    public void loadRenderRules() {
        renderRules = new TileRenderRules(new IntRectangle[]{
                new IntRectangle(0, 16, 16, 16)});

        addSandRules(256, 160, Tiles.SAND, new IntRectangle(0, 48, 16, 16));
        addSandRules(256, 160, Tiles.FERTILIZED_GROUND, new IntRectangle(240, 208, 16, 16));
        addSandRules(256, 160, Tiles.GROUND_LIGHT, new IntRectangle(160, 16, 16, 16));
    }
}
