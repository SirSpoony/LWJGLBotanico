package me.spoony.botanico.common.level;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.util.position.ChunkPosition;
import me.spoony.botanico.common.util.position.TilePosition;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Colten on 11/25/2016.
 */
public interface IPlane
{
    int OVERWORLD = 0;
    int UNDERWORLD = 1;

    Tile getTile(TilePosition position);
    Building getBuilding(TilePosition position);
    byte getBuildingData(TilePosition position);
    Collection<Entity> getEntities();
    Entity getEntity(int eid);
    boolean isLocal();

    Chunk getChunk(ChunkPosition position);

    int getID();
}
