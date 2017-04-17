package me.spoony.botanico.common.buildings.buildingentity;

import me.spoony.botanico.common.dialog.DialogKnappingStation;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.OmniPosition;

/**
 * Created by Colten on 11/27/2016.
 */
public class BuildingEntityKnappingStation extends BuildingEntity
{
    public DialogKnappingStation dialog;

    public Inventory getInventory() {
        return dialog.inventory;
    }

    public BuildingEntityKnappingStation(OmniPosition position, IPlane plane)
    {
        super(position, plane);

        DialogKnappingStation dks = new DialogKnappingStation();
        this.dialog = dks;
    }
}
