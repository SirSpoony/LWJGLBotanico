package me.spoony.botanico.common.buildings.buildingentity;

import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.OmniPosition;

/**
 * Created by Colten on 11/10/2016.
 */
public class BuildingEntityWorkbench extends BuildingEntity {
    public Inventory inventory;

    public BuildingEntityWorkbench(OmniPosition position, IPlane plane) {
        super(position, plane);
        //inventory = new Inventory(19, Dialog.);
    }
}
