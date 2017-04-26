package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.buildings.buildingentity.BuildingEntity;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntityKnappingStation;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colten on 11/27/2016.
 */
public class BuildingKnappingStation extends Building implements IBuildingEntityHost {

  public BuildingKnappingStation(int id) {
    super(id);
    this.name = "knapping_station";
    this.collisionBounds = new DoubleRectangle(0, 0, 1, 1);
    this.setTextureBounds(0, 176, 16, 32);
    this.hardness = 1f;
  }

  @Override
  public ItemStack[] getDrops(IPlane level, OmniPosition position) {
    if (!(level instanceof ServerPlane)) {
      return null;
    }
    ServerPlane serverLevel = (ServerPlane) level;
    BuildingEntityKnappingStation bew = (BuildingEntityKnappingStation) serverLevel
        .getBuildingEntity(position);
    List<ItemStack> items = new ArrayList<ItemStack>();
    for (int i = 0; i < bew.getInventory().getLength(); i++) {
      if (bew.getInventory().getSlot(i).getStack() == null) {
        continue;
      }
      items.add(bew.getInventory().getSlot(i).getStack());
    }
    items.add(new ItemStack(Items.KNAPPING_STATION));

    return items.toArray(new ItemStack[items.size()]);
  }

  @Override
  public void onClick(IPlane level, EntityPlayer player, OmniPosition position) {
    if (!(level instanceof ServerPlane)) {
      return;
    }
    ServerPlane serverLevel = (ServerPlane) level;

    BuildingEntityKnappingStation beks = (BuildingEntityKnappingStation) serverLevel
        .getBuildingEntity(position);
    if (beks != null) {
      if (player instanceof RemoteEntityPlayer) {
        ((RemoteEntityPlayer) player).openDialog(beks.dialog);
      }
    }
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.WOOD;
  }

  @Override
  public BuildingEntity createNewEntity(IPlane plane, OmniPosition position) {
    return new BuildingEntityKnappingStation(position, plane);
  }
}
