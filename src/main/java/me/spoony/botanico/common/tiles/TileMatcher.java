package me.spoony.botanico.common.tiles;

/**
 * Created by Colten on 11/8/2016.
 */
public class TileMatcher
{
    protected Direction direction;
    protected TileMatcherType mode;
    protected Tile tile;

    public TileMatcher(Direction direction, TileMatcherType mode, Tile tile)
    {
        this.direction = direction;
        this.mode = mode;
        this.tile = tile;
    }

    public boolean matches(AdjacentTiles tiles)
    {
        if (tiles == null) return false;
        if (mode == TileMatcherType.ANYTHING) return true;
        if (mode == TileMatcherType.REQUIRE && tiles.getTile(this.direction) == tile) return true;
        if (mode == TileMatcherType.EXCEPT && tiles.getTile(this.direction) != tile) return true;
        return false;
    }
}
