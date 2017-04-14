package me.spoony.botanico.common.tiles;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Colten on 11/19/2016.
 */
public class TileRegistry
{
    protected Map<Integer, Tile> tiles;

    public TileRegistry()
    {
        tiles = new HashMap<>();
    }


    public void register(Tile tile)
    {
        Preconditions.checkNotNull(tile);
        tiles.put(tile.getID(), tile);
    }

    public Tile get(String name)
    {
        Preconditions.checkNotNull(Strings.emptyToNull(name), "Tile name cannot be empty or null");
        for (Tile i : tiles.values())
        {
            if (i.getName().equalsIgnoreCase(name)) return i;
        }
        return null;
    }

    public Tile get(int id)
    {
        Preconditions.checkArgument(tiles.containsKey(id), "ID "+id+" is not in tile registry.");
        return tiles.get(id);
    }

    public List<Tile> getRegisteredTiles() {
        return Lists.newArrayList(tiles.values());
    }
}
