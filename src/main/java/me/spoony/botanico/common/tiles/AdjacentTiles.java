package me.spoony.botanico.common.tiles;

/**
 * Created by Colten on 11/8/2016.
 */
public class AdjacentTiles
{
    protected Tile[] tiles;

    public AdjacentTiles()
    {
        tiles = new Tile[8];
    }

    public void setTile(Direction direction, Tile tile)
    {
        tiles[direction.getValue()] = tile;
    }

    public Tile getTile(Direction direction)
    {
        return tiles[direction.getValue()];
    }
}