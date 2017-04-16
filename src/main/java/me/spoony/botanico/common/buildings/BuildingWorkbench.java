package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.buildings.buildingentity.BuildingEntity;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntityWorkbench;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.level.ServerPlane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colten on 11/10/2016.
 */
public class BuildingWorkbench extends Building implements IBuildingEntityHost {

  public BuildingWorkbench(int id) {
    super(id);
    this.name = "workbench";
    this.collisionBounds = new DoubleRectangle(0, 0, 1, 1);
    this.setTextureBounds(0, 208, 16, 32);
    this.hardness = 1f;
  }

  @Override
  public ItemStack[] getDrops(IPlane level, TilePosition position) {
    if (!(level instanceof ServerPlane)) {
      return null;
    }
    ServerPlane serverLevel = (ServerPlane) level;
    BuildingEntityWorkbench bew = (BuildingEntityWorkbench) serverLevel.getBuildingEntity(position);
    List<ItemStack> items = new ArrayList<ItemStack>();
    for (int i = 0; i < bew.inventory.getLength(); i++) {
      if (bew.inventory.getSlot(i).getStack() == null) {
        continue;
      }
      items.add(bew.inventory.getSlot(i).getStack());
    }
    items.add(new ItemStack(Items.WORKBENCH));

    return items.toArray(new ItemStack[items.size()]);
  }

  @Override
  public BuildingEntity createNewEntity(IPlane plane, TilePosition position) {
    return new BuildingEntityWorkbench(position, plane);
  }
}

