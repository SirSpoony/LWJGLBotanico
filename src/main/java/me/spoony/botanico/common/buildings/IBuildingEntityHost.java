package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.buildings.buildingentity.BuildingEntity;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.OmniPosition;

/**
 * Created by Colten on 12/30/2016.
 */
public interface IBuildingEntityHost {

  BuildingEntity createNewEntity(IPlane level, OmniPosition position);
}
