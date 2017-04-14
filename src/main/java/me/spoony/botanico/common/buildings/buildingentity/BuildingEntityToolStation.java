package me.spoony.botanico.common.buildings.buildingentity;

import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.common.dialog.DialogToolStation;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.items.ItemSlotMode;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 11/12/2016.
 */
public class BuildingEntityToolStation extends BuildingEntity
{
    public Dialog dialog;

    public Inventory getInventory() {
        return dialog.inventory;
    }

    public BuildingEntityToolStation(TilePosition position, IPlane plane)
    {
        super(position, plane);

        DialogToolStation dts = new DialogToolStation();
        this.dialog = dts;
    }
}
