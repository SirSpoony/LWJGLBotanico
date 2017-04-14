package me.spoony.botanico.common.items;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/10/2016.
 */
    public class ItemBuilding extends Item
    {
        protected Building building;

        public Building getBuilding()
        {
            return building;
        }

        public ItemBuilding(int id, IntRectangle texturebounds, String name, Building building, int maxstacksize)
        {
            super(id, texturebounds, name, maxstacksize);
            this.building = building;
        }
    }
