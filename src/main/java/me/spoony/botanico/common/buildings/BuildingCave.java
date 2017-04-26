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
  public void render(RendererGame rg, ClientPlane level, OmniPosition position, int d,
      Color color) {
    rg.sprite(position.add(PositionType.GAME, -1, -1), getTextureSheet(),
        new IntRectangle(192 + (d == 1 ? 48 : 0), 0, 48, 48), color, position.getGameY() + 3);
  }

  @Override
  public void onClick(IPlane plane, EntityPlayer player, OmniPosition position) {
    if (plane.isLocal()) {
      return;
    }

    ServerPlane serverPlane = (ServerPlane) plane;
    int data = serverPlane.getBuildingData(position);
    ItemStack heldStack = ((RemoteEntityPlayer) player).getHeldSlot().getStack();

    if (data == 1) {
      ((RemoteEntityPlayer) player)
          .teleport(position, serverPlane.getLevel().getUnderworld());
    } else {
      if (heldStack != null && heldStack.getItem() == Items.ROPE) {
        serverPlane.setBuildingData(position, 1);
        serverPlane.getLevel().getUnderworld().setBuilding(position, Buildings.CAVE_ROPE);
        heldStack.increaseCount(-1);
      } else {
        ((RemoteEntityPlayer) player).sendMessage("You need rope first!");
      }
    }

  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.ROCK;
  }
}
