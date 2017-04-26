package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;
import me.spoony.botanico.server.level.ServerPlane;
import me.spoony.botanico.client.ClientPlane;

/**
 * Created by Colten on 11/10/2016.
 */
public class BuildingTree extends Building {

  public BuildingTree(int id) {
    super(id);
    this.name = "tree";
    this.hardness = 10;
  }

  @Override
  public void render(RendererGame rg, ClientPlane level, OmniPosition position, int d,
      Color color) {
    if (d == 1) {
      position.setX(PositionType.GAME, position.getX(PositionType.GAME) - 1);
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(16, 208, 48, 64), color, position.getGameY());
    } else {
      position.setX(PositionType.GAME, position.getX(PositionType.GAME) - 1);
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(128, 208, 48, 64), color, position.getGameY());
    }

  }

  @Override
  public void destroy(IPlane level, OmniPosition position) {
    if (!(level instanceof ServerPlane)) {
      return;
    }
    ServerPlane serverLevel = (ServerPlane) level;
    serverLevel.setBuilding(position, Buildings.STICKS_PILE);
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.WOOD;
  }
}
