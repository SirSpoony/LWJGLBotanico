package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 12/30/2016.
 */
public class BuildingCave extends Building {

  public BuildingCave(int id) {
    super(id);
    this.name = "cave";
    this.hardness = Float.MAX_VALUE;
    this.alwaysBehindCharacter = true;
    this.collisionBounds = new DoubleRectangle(0, 0, 1, 1);
  }

  @Override
  public void render(RendererGame rg, ClientPlane level, OmniPosition position, byte d,
      Color color) {
    rg.sprite(position.add(PositionType.GAME,-1,-1), getTextureSheet(),
        new IntRectangle(192 + (d == 1 ? 48 : 0), 0, 48, 48), color, position.getGameY()+3);
  }

  @Override
  public boolean onClick(IPlane plane, EntityPlayer player, OmniPosition position) {
    if (plane.isLocal()) {
      return true;
    }
    if (player == null) {
      return true;
    }
    ServerPlane serverPlane = (ServerPlane) plane;
    ItemStack heldStack = ((RemoteEntityPlayer) player).getHeldSlot().getStack();

    if (heldStack != null && heldStack.getItem() == Items.ROPE) {
      if (plane.getBuildingData(position) == 1) {
        return true;
      }
      serverPlane.setBuildingData(position, (byte) 1);
      serverPlane.getLevel().getUnderworld().setBuilding(position, Buildings.CAVE_ROPE);
      heldStack.increaseCount(-1);
      return true;
    }

    if (serverPlane.getBuildingData(position) == (byte) 1) {
      ((RemoteEntityPlayer) player)
          .teleport(position, serverPlane.getLevel().getUnderworld());
      return true;
    } else {
      ((RemoteEntityPlayer) player).sendMessage("You need rope first!");
    }

    return true;
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.ROCK;
  }
}
