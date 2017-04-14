package me.spoony.botanico.common.items;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.buildings.Buildings;

/**
 * Created by Colten on 11/12/2016.
 */
public enum ItemToolType
{
    BLADE (),
    HOE (),
    AXE (Buildings.TREE, Buildings.TOOL_STATION, Buildings.KNAPPING_STATION),
    PICKAXE (Buildings.BOULDER, Buildings.COAL_ORE, Buildings.COPPER_ORE);

    private Building[] affectedBuildings;

    ItemToolType(Building... affectedBuildings) {
        this.affectedBuildings = affectedBuildings;
    }

    public Building[] getAffectedBuildings() {
        return affectedBuildings;
    }
}
