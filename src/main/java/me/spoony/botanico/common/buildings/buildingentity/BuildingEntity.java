package me.spoony.botanico.common.buildings.buildingentity;

import com.google.common.base.Preconditions;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 11/8/2016.
 */
public class BuildingEntity
{
    public TilePosition position;
    public IPlane plane;

    public BuildingEntity(TilePosition position, IPlane plane) {
        Preconditions.checkNotNull(position);
        Preconditions.checkNotNull(plane);

        this.position = position;
        this.plane = plane;
    }

    public void create() {

    }

    public void destroy() {

    }
}
