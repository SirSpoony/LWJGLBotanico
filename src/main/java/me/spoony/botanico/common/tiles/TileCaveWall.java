package me.spoony.botanico.common.tiles;

import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 1/2/2017.
 */
public class TileCaveWall extends Tile {
    public TileCaveWall(int id, String name) {
        super(id, name);
        this.footStepMaterial = FootStepMaterial.GRASS; // todo rock sound
        this.setShouldCollide(true);
    }

    @Override
    public void loadRenderRules() {
        renderRules = new TileRenderRules(new IntRectangle[]{
                new IntRectangle(0, 320, 16, 16)});

        renderRules.addRule(new IntRectangle(32,336,16,16),
                new TileMatcher(Direction.UP, TileMatcherType.REQUIRE, Tiles.CAVE_WALL),
                new TileMatcher(Direction.DOWN, TileMatcherType.REQUIRE, Tiles.CAVE_FLOOR));

        renderRules.addRule(new IntRectangle(32,304,16,16),
                new TileMatcher(Direction.DOWN, TileMatcherType.REQUIRE, Tiles.CAVE_WALL),
                new TileMatcher(Direction.UP, TileMatcherType.REQUIRE, Tiles.CAVE_FLOOR));

        renderRules.addRule(new IntRectangle(48,320,16,16),
                new TileMatcher(Direction.LEFT, TileMatcherType.REQUIRE, Tiles.CAVE_WALL),
                new TileMatcher(Direction.RIGHT, TileMatcherType.REQUIRE, Tiles.CAVE_FLOOR));

        renderRules.addRule(new IntRectangle(16,320,16,16),
                new TileMatcher(Direction.RIGHT, TileMatcherType.REQUIRE, Tiles.CAVE_WALL),
                new TileMatcher(Direction.LEFT, TileMatcherType.REQUIRE, Tiles.CAVE_FLOOR));
    }
}