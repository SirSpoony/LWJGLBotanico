package me.spoony.botanico.common.buildings;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Colten on 11/12/2016.
 */
public class BuildingRegistry {

  protected Map<Integer, Building> buildings;

  public BuildingRegistry() {
    buildings = new HashMap<Integer, Building>();
  }


  public void registerBuilding(Building building) {
    Preconditions.checkNotNull(building);
    buildings.put(building.getID(), building);
  }

  public Building getBuilding(String name) {
    Preconditions.checkNotNull(name);
    for (Building b : buildings.values()) {
      if (b.getName().equalsIgnoreCase(name)) {
        return b;
      }
    }
    return null;
  }

  public Building getBuilding(int id) {
    if (id == -1) {
      return null;
    }
    Preconditions
        .checkArgument(buildings.containsKey(id), "ID " + id + "is not in building registry.");
    return buildings.get(id);
  }
}
