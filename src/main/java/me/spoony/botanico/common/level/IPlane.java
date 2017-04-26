package me.spoony.botanico.common.level;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.common.tiles.Tile;

import java.util.Collection;
import me.spoony.botanico.common.util.position.OmniPosition;

/**
 * Created by Colten on 11/25/2016.
 */
public interface IPlane
{
    int OVERWORLD = 0;
    int UNDERWORLD = 1;

    Tile getTile(OmniPosition position);
    Tile getTile(long x, long y);
    Building getBuilding(OmniPosition position);
    Building getBuilding(long x, long y);
    int getBuildingData(OmniPosition position);
    int getBuildingData(long x, long y);
    Collection<Entity> getEntities();
    Entity getEntity(int eid);
    boolean isLocal();

    Chunk getChunk(long x, long y);

    int getID();
}
