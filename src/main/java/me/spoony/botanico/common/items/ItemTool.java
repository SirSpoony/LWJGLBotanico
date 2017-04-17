package me.spoony.botanico.common.items;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.buildings.Buildings;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

import java.util.Arrays;

/**
 * Created by Colten on 11/12/2016.
 */
public class ItemTool extends Item {

  protected final ItemToolType toolType;
  protected final float toolPower;


  public ItemTool(int id, IntRectangle texturebounds, String name, ItemToolType toolType,
      float toolPower) {
    super(id, texturebounds, name, 1);
    this.toolType = toolType;
    this.toolPower = toolPower;
  }

  @Override
  public void onUse(IPlane level, EntityPlayer player, ItemSlot cursor, OmniPosition position) {
    if (toolType == ItemToolType.HOE) {
      if (level instanceof ServerPlane) {
        Tile tile = level.getTile(position);

        boolean valid = true;
        for (int ox = -2; ox <= 2; ox++) {
          for (int oy = -2; oy <= 2; oy++) {
            Tile otile = level.getTile(new OmniPosition(position).add(PositionType.GAME, ox, oy));
            if (otile != Tiles.GROUND && otile != Tiles.FERTILIZED_GROUND) {
              valid = false;
            }
          }
        }
        if (!valid) {
          if (player instanceof RemoteEntityPlayer) {
            ((RemoteEntityPlayer) player).sendMessage("That ground is too dry...");
          }
          return;
        }

        if (level.getBuilding(position) == Buildings.GRASS) {
          ((ServerPlane) level).breakBuildingAndDrop(position, player);
        }
        if (tile == Tiles.GROUND && level.getBuilding(position) == null) {
          ((ServerPlane) level).setTile(position, Tiles.FERTILIZED_GROUND);
        }
      }
    }
  }

  @Override
  public float getBuildingDamageModifier(Building b) {
    if (Arrays.asList(getToolType().getAffectedBuildings()).contains(b)) {
      return getToolPower();
    }
    return 1;
  }

  public ItemToolType getToolType() {
    return toolType;
  }

  public float getToolPower() {
    return toolPower;
  }
}
