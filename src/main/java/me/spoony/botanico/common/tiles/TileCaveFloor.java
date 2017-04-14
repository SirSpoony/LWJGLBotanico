package me.spoony.botanico.common.tiles;

import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 1/1/2017.
 */
public class TileCaveFloor extends Tile {
    public TileCaveFloor(int id, String name) {
        super(id, name);
        this.footStepMaterial = FootStepMaterial.GRASS; // todo rock sound
    }

    @Override
    public void loadRenderRules() {
        renderRules = new TileRenderRules(new IntRectangle[]{
                new IntRectangle(0, 288, 16, 16)});
    }
}